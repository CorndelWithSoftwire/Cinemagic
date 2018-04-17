import * as React from 'react';
import LoadingSpinner from '../loadingSpinner';

export default class SeatSelect extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            seats: null,
        };

        this.onSeatClick = this.onSeatClick.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.props.api.get(`/api/bookings?showingId=${encodeURIComponent(this.props.showing.id)}`)
            .then(bookings => this.setState({ seats: this.prepareSeats(bookings) }))
            .catch(error => this.props.errorHandler.onUnrecoverableError(error));
    }

    onSeatClick(rowIndex, seatIndex) {
        this.setState((state) => {
            const oldSeat = state.seats[rowIndex][seatIndex];
            oldSeat.selected = !oldSeat.selected && !oldSeat.booked;
            return state;
        });
    }

    /**
     * @return Array(Array(seat) 2D array of the seats in the screen
     */
    prepareSeats(bookings) {
        const seats = [];
        for (let i = 0; i < this.props.showing.screen.rows; i += 1) {
            seats[i] = [];
            for (let j = 0; j < this.props.showing.screen.rowWidth; j += 1) {
                const booked = bookings
                    .some(booking => booking.seatRow === i && booking.seatNumber === j);

                seats[i][j] = {
                    selected: false,
                    booked,
                };
            }
        }
        return seats;
    }

    isLoaded() {
        return this.state.seats !== null;
    }

    handleSubmit() {
        const newBookings = [];
        this.state.seats.forEach((row, rowIndex) =>
            row.forEach((seat, seatIndex) => {
                if (seat.selected) {
                    newBookings.push({
                        showing: { id: this.props.showing.id },
                        seatRow: rowIndex,
                        seatNumber: seatIndex,
                    });
                }
            }));
        if (newBookings.length === 0) {
            this.props.errorHandler.onRecoverableError('You must select at least one seat.');
        } else {
            this.props.api.post('/api/bookings', { bookings: newBookings })
                .then(({ bookings }) => this.props.bookingComplete(bookings))
                .catch(error => this.props.errorHandler.onRecoverableError(`Unable to book seats: ${error.message}`));
        }
    }

    render() {
        if (!this.isLoaded()) {
            return <LoadingSpinner />;
        }

        const seatIcon = (seat) => {
            if (seat.booked) {
                return 'X';
            } else if (seat.selected) {
                return 'O';
            }
            return 'U';
        };

        return (
          <div className="seat-select">
            <h1>Select a seat</h1>
            <div className="seats">
              {this.state.seats.map((row, rowIndex) => (
                <div className="row" key={rowIndex}>
                  {row.map((seat, seatIndex) => (
                    <div className="seat" key={seatIndex}>
                      <button
                        onClick={() => this.onSeatClick(rowIndex, seatIndex)}
                        disabled={seat.booked ? 'disabled' : null}
                      >
                        {seatIcon(seat)}
                      </button>
                    </div>))}
                </div>))
                    }
            </div>
            <button
              onClick={this.handleSubmit}
              id="seat-select-book-button"
            >
                Book!
            </button>
          </div>
        );
    }
}
