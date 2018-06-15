import React from 'react';

export default class AdminSection extends React.Component {
    render() {
        return (
          <div className="admin-section">
            <div className="title-section">
              <h1>{this.props.title}</h1>
            </div>
            <div className="content-section">
              {this.props.children}
            </div>
          </div>
        );
    }
}
