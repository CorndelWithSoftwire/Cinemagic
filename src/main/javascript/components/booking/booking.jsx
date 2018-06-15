import * as React from 'react';
import CinemaSelect from './cinemaSelect';
import ShowingSelect from './showingSelect';
import SeatSelect from './seatSelect';
import ProgressIndicator from './progressIndicator';
import BookingSuccess from './bookingSuccess';
import StepContainer from './stepContainer';

/**
 * The booking page contains every component of the booking process.
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
     * Render the appropriate step for the current booking state
     */
    renderStep() {
        if (this.state.booking) {
            return (
              <StepContainer title="Booking Successful">
                <BookingSuccess reference={this.state.booking[0].reference} />
              </StepContainer>
            );
        } else if (this.state.showing) {
            return (
              <StepContainer title="Choose your Seats">
                <SeatSelect
                  showing={this.state.showing}
                  bookingComplete={this.bookingComplete}
                  api={this.props.api}
                  errorHandler={this.props.errorHandler}
                />
              </StepContainer>
            );
        } else if (this.state.cinema) {
            return (
              <StepContainer title="Select a Showing">
                <ShowingSelect
                  cinema={this.state.cinema}
                  selectShowing={this.selectShowing}
                  api={this.props.api}
                  errorHandler={this.props.errorHandler}
                />
              </StepContainer>
            );
        }
        return (
          <StepContainer title="Select a Cinema">
            <CinemaSelect
              selectCinema={this.selectCinema}
              api={this.props.api}
              errorHandler={this.props.errorHandler}
            />
          </StepContainer>
        );
    }

    render() {
        const steps = [
            { name: 'Cinema', highlighted: !this.state.cinema },
            { name: 'Showing', highlighted: (this.state.cinema && !this.state.showing) },
            { name: 'Seats', highlighted: (this.state.showing && !this.state.booking) },
            { name: 'Confirmation', highlighted: !!this.state.booking },
        ];

        return (
          <div className="booking">
            <ProgressIndicator steps={steps} />
            {this.renderStep()}
          </div>
        );
    }
}
