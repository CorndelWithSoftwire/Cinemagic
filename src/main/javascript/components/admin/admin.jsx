import * as React from 'react';
import { Route, BrowserRouter, Switch, Redirect } from 'react-router-dom';

import Films from './films';
import Cinemas from './cinemas';
import Login from './login';
import Showings from './showings';
import AdminNavBar from './adminNavBar';
import AdminSection from './adminSection';


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
                <AdminSection title="Cinemas">
                  <Cinemas
                    {...props}
                    api={this.props.api}
                    errorHandler={this.props.errorHandler}
                  />
                </AdminSection>
              )}
            />
            <Route
              path={`${this.url}/films`}
              render={props => (
                <AdminSection title="Films">
                  <Films
                    {...props}
                    api={this.props.api}
                    errorHandler={this.props.errorHandler}
                  />
                </AdminSection>
              )}
            />
            <Route
              path={`${this.url}/showings`}
              render={props => (
                <AdminSection title="Showings">
                  <Showings
                    {...props}
                    api={this.props.api}
                    errorHandler={this.props.errorHandler}
                  />
                </AdminSection>
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
                />
              )}
            />
          </Switch>
        );

        const login = (
          <AdminSection title="Login">
            <Login
              authHolder={this.props.authHolder}
              api={this.props.api}
              onSuccess={this.onLoginSuccess}
              errorHandler={this.props.errorHandler}
            />
          </AdminSection>
        );

        return (
          <div className="admin">
            <BrowserRouter>
              <div>
                <AdminNavBar />
                <div className="content">
                  {this.props.authHolder.isAuthenticated ? routes : login}
                </div>
              </div>
            </BrowserRouter>
          </div>
        );
    }
}
