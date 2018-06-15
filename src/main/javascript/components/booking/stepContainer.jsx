import React from 'react';

export default class StepContainer extends React.Component {
    render() {
        return (
          <div className="step-container">
            <div className="title-section">
              <h1>{this.props.title}</h1>
            </div>
            <div className="step-section">
              {this.props.children}
            </div>
          </div>
        );
    }
}
