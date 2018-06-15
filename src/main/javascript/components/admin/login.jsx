import React from 'react';

export default class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(event) {
        event.preventDefault();
        this.props.authHolder.authenticate(this.state.username, this.state.password)
            .then((success) => {
                if (success) {
                    this.props.onSuccess();
                } else {
                    this.props.errorHandler.onRecoverableError('Username or Password incorrect');
                }
            })
            .catch(error => this.props.errorHandler.onUnrecoverableError(error));
    }

    onChange(event) {
        this.setState({ [event.target.name]: event.target.value });
    }

    render() {
        return (
          <div className="login">
            <div className="centered-form-container">
              <form onSubmit={this.onSubmit} id="login-form">
                <label htmlFor="login-form-username-field">Username</label>
                <input
                  id="login-form-username-field"
                  type="text"
                  name="username"
                  value={this.state.username}
                  onChange={this.onChange}
                />

                <label htmlFor="login-form-password-field">Password</label>
                <input
                  id="login-form-password-field"
                  type="password"
                  name="password"
                  value={this.state.password}
                  onChange={this.onChange}
                />

                <button id="login-form-submit-button" className="submit-button" type="submit">Login!</button>
              </form>
            </div>
          </div>
        );
    }
}
