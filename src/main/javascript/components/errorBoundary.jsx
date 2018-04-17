import * as React from 'react';

/**
 * ErrorBoundary which catches unrecoverable errors.  Hopefully the user will never see this :)
 */
export default class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    componentDidCatch() {
        this.setState({ hasError: true });
    }

    render() {
        if (this.state.hasError) {
            return (
              <div className="errorBoundary">
                <h1>Oh dear, something has gone wrong</h1>
                <p>Click <a href="/">here</a> to try again.</p>
              </div>
            );
        }
        return this.props.children;
    }
}
