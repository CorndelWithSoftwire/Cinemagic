import * as React from "react";
import CinemaSelect from "./cinemaSelect";
import FilmTimeSelect from "./filmTimeSelect";
import SeatSelect from "./seatSelect";

/**
 * Booking widget contains every component of the booking process.
 */
export default class Booking extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            cinema: null,
            film: null,
            showing: null
        };
        this.selectCinema = this.selectCinema.bind(this);
        this.selectShowing = this.selectShowing.bind(this);
    }

    render() {
        return (
            <div className="booking">
                <h2>Book Seats</h2>
                {this.renderWidget()}
            </div>
        );
    }

    /**
     * Render the appropriate widget(s) for the current booking state
     */
    renderWidget() {
        if (this.state.showing) {
            return <SeatSelect showing={this.state.showing}/>
        } else if (this.state.cinema) {
            return <FilmTimeSelect cinema={this.state.cinema} selectShowing={this.selectShowing}/>;
        } else {
            return <CinemaSelect selectCinema={this.selectCinema} />;
        }
    }

    selectCinema(cinema) {
        this.setState({ cinema: cinema });
    }

    selectShowing(showing) {
        this.setState({ showing: showing });
    }
}
