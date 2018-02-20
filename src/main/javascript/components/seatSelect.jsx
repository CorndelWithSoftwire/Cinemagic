import * as React from "react";

export default class SeatSelect extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            bookings: [],
            seats: []
        };
        this.onSeatClick = this.onSeatClick.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.loadBookings();
    }

    render() {
        return (
            <div className="seat-select">
                <div>Screen</div>
                <div className="seats">
                    {this.state.seats.map((row, rowIndex) =>
                        <div className="row" key={rowIndex}>
                            {row.map((seat, seatIndex) =>
                                <div className="seat" key={seatIndex}>
                                    <button onClick={() => this.onSeatClick(rowIndex, seatIndex)} disabled={seat.booked ? "disabled" : null}>
                                        {seat.booked ? 'X' : seat.selected ? 'O' : 'U'}
                                    </button>
                                </div>
                            )}
                        </div>
                    )}
                </div>
                <button onClick={this.handleSubmit}>Book!</button>
            </div>
        );
    }

    loadBookings() {
        fetch("/api/bookings?showingId=" + encodeURIComponent(this.props.showing.id)).then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                // TODO errors
                throw new Error("Error response", response);
            }
        }).then(bookings => {
            this.setState({ bookings: bookings, seats: this.prepareSeats(bookings) });
        });
    }

    /**
     * @return Array(Array(seat) 2D array of the seats in the screen
     */
    prepareSeats(bookings) {
        const seats = [];
        for (let i = 0; i < this.props.showing.screen.rows; i++) {
            seats[i] = [];
            for (let j = 0; j < this.props.showing.screen.rowWidth; j++) {
                const booked = bookings.some(booking => {
                    return booking.seatRow === i && booking.seatNumber === j;
                });

                seats[i][j] = {
                    selected: false,
                    booked: booked
                }
            }
        }
        return seats;
    }

    onSeatClick(rowIndex, seatIndex) {
        this.setState(state => {
            const oldSeat = state.seats[rowIndex][seatIndex];
            oldSeat.selected = !oldSeat.selected && !oldSeat.booked;
            return state;
        });
    }

    handleSubmit() {
        const bookings = [];
        this.state.seats.forEach((row, rowIndex) =>
            row.forEach((seat, seatIndex) => {
                if (seat.selected) {
                    bookings.push({
                        showing: { id: this.props.showing.id },
                        seatRow: rowIndex,
                        seatNumber: seatIndex
                    })
                }
            }));
        fetch("/api/bookings", {
            method: "POST",
            body: JSON.stringify(bookings),
            headers: {
                "accept": "application/json",
                "content-type": "application/json"
            }
        }).then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                // TODO: errors
                throw new Error("Error booking seats", response);
            }
        }).then(booking => {
            this.props.bookingComplete(booking);
        });
    }
}
