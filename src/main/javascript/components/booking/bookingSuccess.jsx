import React from 'react';

export default class BookingSuccess extends React.Component {
    render() {
        return (
            <div className="booking-success">
                <div className="title-section">
                    <h1>Booking Successful</h1>
                </div>
                <div className="selection-section">
                    <div>Booking complete, reference {this.props.reference}</div>
                </div>
            </div>
        );
    }
}
