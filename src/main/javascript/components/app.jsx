import * as React from 'react';
import { BrowserRouter, Route, Switch, Link, Redirect } from 'react-router-dom';
import Api from '../api';
import Booking from './booking/booking';
import Admin from './admin/admin';
import ErrorModal from './errorModal';
import AuthHolder from './admin/authHolder';

export default class App extends React.Component {
    constructor(props) {
        super(props);

        this.authHolder = new AuthHolder();
        this.api = new Api(this.authHolder, this.onUnrecoverableError.bind(this));
        this.errorHandler = {
            onRecoverableError: this.onRecoverableError.bind(this),
            onUnrecoverableError: this.onUnrecoverableError.bind(this),
        };

        this.state = {
            recoverableError: null,
        };

        this.onCloseErrorModal = this.onCloseErrorModal.bind(this);
    }

    onRecoverableError(message) {
        this.setState({ recoverableError: message });
    }

    onUnrecoverableError(message) {
        this.setState({ unrecoverableError: message });
    }

    onCloseErrorModal() {
        this.setState({ recoverableError: null });
    }

    render() {
        if (this.state.unrecoverableError) {
            // Rethrow to be caught by the error boundary.  This seems like a bit of a hack.
            // Wrap in an error just in case.
            throw new Error(this.state.unrecoverableError);
        }

        const routes = (
          <Switch>
            <Route
              exact
              path="/booking"
              render={props => (
                <Booking {...props} api={this.api} errorHandler={this.errorHandler} />
                    )}
            />
            <Route
              path="/admin"
              render={props => (
                <Admin
                  {...props}
                  api={this.api}
                  authHolder={this.authHolder}
                  errorHandler={this.errorHandler}
                />
                    )}
            />
            <Route
              exact
              path="/"
              render={props => (
                <Redirect {...props} to="/booking" />)
                    }
            />
          </Switch>
        );

        const navbar = (
          <ul className="app-navbar">
            <li className="title">
              <Link to="/">Cinemagic</Link>
            </li>
          </ul>
        );

        return (
          <div className="app">
            <div className="main-content">
              {this.state.recoverableError ? <ErrorModal
                message={this.state.recoverableError}
                onClose={this.onCloseErrorModal}
              /> : ''}
              <BrowserRouter>
                <div>
                  {navbar}
                  <hr />
                  {routes}
                </div>
              </BrowserRouter>
            </div>
          </div>
        );
    }
}
