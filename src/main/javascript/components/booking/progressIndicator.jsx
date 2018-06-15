import React from 'react';

export default class ProgressIndicator extends React.Component {
    render() {
        return (
          <div className="progress-indicator">
            {this.props.steps.map((step, i) =>
              <div key={i} className={`progress-step${step.highlighted ? ' highlighted' : ''}`}>{step.name}</div>)}
          </div>
        );
    }
}
