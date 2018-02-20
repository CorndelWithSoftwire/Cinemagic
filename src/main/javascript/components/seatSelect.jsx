import * as React from "react";

export default class SeatSelect extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            seats: this.prepareSeats()
        }
    }

    render() {
        return (
            <div className="seat-select">
                <div>Screen</div>
                <div className="seats">
                    {this.state.seats.map((row, rowIndex) =>
                        <div className="row" key={rowIndex}>
                            {row.map((seat, seatIndex) =>
                                <div className="seat" key={seatIndex}>{seat.available ? 'U' : 'X'}</div>
                            )}
                        </div>
                    )}
                </div>
            </div>
        );
    }

    /**
     * @return Array(Array(seat) 2D array of the seats in the screen
     */
    prepareSeats() {
        const seats = [];
        for (let i = 0; i < this.props.showing.screen.rows; i++) {
            seats[i] = [];
            for (let j = 0; j < this.props.showing.screen.rowWidth; j++) {
                seats[i][j] = {
                    selected: false,
                    available: true
                }
            }
        }
        return seats;
    }
}
