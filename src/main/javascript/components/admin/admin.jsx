import * as React from 'react';
import { Link, Route, BrowserRouter, Switch, Redirect } from 'react-router-dom';

import Films from './films';
import Cinemas from './cinemas';
import Login from './login';
import Showings from './showings';


export default class Admin extends React.Component {
    constructor(props) {
        super(props);
        this.url = props.match.url;
        this.onLoginSuccess = this.onLoginSuccess.bind(this);
    }

    onLoginSuccess() {
        this.forceUpdate();
    }

    render() {
        const routes = (
          <Switch>
            <Route
              path={`${this.url}/cinemas`}
              render={props => (
                <Cinemas
                  {...props}
                  api={this.props.api}
                  errorHandler={this.props.errorHandler}
                />
                    )}
            />
            <Route
              path={`${this.url}/films`}
              render={props => (
                <Films
                  {...props}
                  api={this.props.api}
                  errorHandler={this.props.errorHandler}
                />
                    )}
            />
            <Route
              path={`${this.url}/showings`}
              render={props => (
                <Showings
                  {...props}
                  api={this.props.api}
                  errorHandler={this.props.errorHandler}
                />
                    )}
            />
            <Route
              path={`${this.url}/login`}
              render={props => (
                <Login
                  {...props}
                  authHolder={this.props.authHolder}
                  api={this.props.api}
                  errorHandler={this.props.errorHandler}
                />
                    )}
            />
            <Route
              exact
              path={this.url}
              render={props => (
                <Redirect
                  {...props}
                  to={`${this.url}/films`}
                />)
                    }
            />
          </Switch>
        );
        const login = (
          <Login
            authHolder={this.props.authHolder}
            api={this.props.api}
            onSuccess={this.onLoginSuccess}
            errorHandler={this.props.errorHandler}
          />
        );

        const navbar = (
          <ul className="admin-navbar">
            <li className="navbar-title"><h1>Administration</h1></li>
            <li className="navbar-link"><Link to={`${this.url}/cinemas`} id="admin-link-cinemas">Cinemas</Link></li>
            <li className="navbar-link"><Link to={`${this.url}/films`} id="admin-link-films">Films</Link></li>
            <li className="navbar-link"><Link to={`${this.url}/showings`} id="admin-link-showings">Showings</Link></li>
          </ul>
        );

        return (
          <div className="admin">
            <BrowserRouter>
              <div>
                {navbar}
                <div className="content">
                  {this.props.authHolder.isAuthenticated ? routes : login}
                </div>
              </div>
            </BrowserRouter>
          </div>
        );
    }
}
