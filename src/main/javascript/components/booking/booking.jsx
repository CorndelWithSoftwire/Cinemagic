import * as React from 'react';
import CinemaSelect from './cinemaSelect';
import ShowingSelect from './showingSelect';
import SeatSelect from './seatSelect';
import ProgressIndicator from "./progressIndicator";

/**
 * Booking widget contains every component of the booking process.
 */
export default class Booking extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cinema: null,
            showing: null,
            booking: null,
        };
        this.selectCinema = this.selectCinema.bind(this);
        this.selectShowing = this.selectShowing.bind(this);
        this.bookingComplete = this.bookingComplete.bind(this);
    }

    selectCinema(cinema) {
        this.setState({ cinema });
    }

    selectShowing(showing) {
        this.setState({ showing });
    }

    bookingComplete(booking) {
        this.setState({ booking });
    }

    /**
     * Render the appropriate widget(s) for the current booking state
     */
    renderWidget() {
        if (this.state.booking) {
            return (
              <div className="booking-success">
                <h1>Booking Successful!</h1>
                <div>Booking complete, reference {this.state.booking[0].reference}</div>
              </div>
            );
        } else if (this.state.showing) {
            return (
              <SeatSelect
                showing={this.state.showing}
                bookingComplete={this.bookingComplete}
                api={this.props.api}
                errorHandler={this.props.errorHandler}/>
            );
        } else if (this.state.cinema) {
            return (
              <ShowingSelect
                cinema={this.state.cinema}
                selectShowing={this.selectShowing}
                api={this.props.api}
                errorHandler={this.props.errorHandler}/>
            );
        }
        return (
          <CinemaSelect
            selectCinema={this.selectCinema}
            api={this.props.api}
            errorHandler={this.props.errorHandler}/>
        );
    }

    render() {
        const steps = [
            {name: 'Cinema', highlighted: !this.state.cinema},
            {name: 'Showing', highlighted: (this.state.cinema && !this.state.showing)},
            {name: 'Seats', highlighted: (this.state.showing && !this.state.booking)},
            {name: 'Confirmation', highlighted: !!this.state.booking}
        ];

        return (
          <div className="booking">
              <ProgressIndicator steps={steps}/>
            {this.renderWidget()}
          </div>
        );
    }
}
