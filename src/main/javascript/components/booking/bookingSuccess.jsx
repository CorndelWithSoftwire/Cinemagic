import React from 'react';

export default class BookingSuccess extends React.Component {
    render() {
        return (
            <div className="booking-success">
                Booking complete, reference <span className="reference">{this.props.reference}</span>
            </div>
        );
    }
}
