import * as React from 'react';
import LoadingSpinner from '../loadingSpinner';
import Screens from './screens';

export default class Cinemas extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cinemas: null,
            name: '',
        };

        this.onSubmit = this.onSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    componentDidMount() {
        this.props.api.get('/api/cinemas')
            .then(data => this.setState({ cinemas: data }))
            .catch(error => this.props.errorHandler.onUnrecoverableError(error));
    }

    onChange(event) {
        this.setState({ [event.target.name]: event.target.value });
    }

    onClickDeleteCinema(id) {
        this.props.api.del(`/api/admin/cinemas/${id}`)
            .then(() => this.setState({
                cinemas: this.state.cinemas.filter(cinema => cinema.id !== id),
            }))
            .catch(error => this.props.errorHandler.onRecoverableError(`Unable to delete cinema: ${error.message}`));
    }

    onSubmit(event) {
        event.preventDefault();
        this.props.api.post('/api/admin/cinemas', { name: this.state.name })
            .then((data) => {
                this.setState({
                    cinemas: this.state.cinemas.concat([data]),
                    name: '',
                });
            })
            .catch(error => this.props.errorHandler.onRecoverableError(`Unable to create cinema: ${error.message}`));
    }

    isLoaded() {
        return this.state.cinemas;
    }

    render() {
        const buildCinemaDetails = cinema => (
          <div key={cinema.id} className="cinema-section">
            <div className="cinema-title">
              <h3 className="cinema-details-name">{cinema.name}</h3>
              <button
                aria-label="Delete cinema"
                className="cinema-details-delete-button delete-button"
                onClick={() => this.onClickDeleteCinema(cinema.id)}
              />
            </div>
            <Screens
              screens={cinema.screens}
              cinemaId={cinema.id}
              api={this.props.api}
              errorHandler={this.props.errorHandler}
            />
          </div>
        );

        return (
          <div className="admin-cinemas">
            {this.isLoaded() ? this.state.cinemas.map(buildCinemaDetails) : <LoadingSpinner />}

            <div className="centered-form-container">
              <h2> Add new cinema </h2>

              <form onSubmit={this.onSubmit}>
                <label htmlFor="cinemas-form-name-field">Name</label>
                <input
                  id="cinemas-form-name-field"
                  type="text"
                  name="name"
                  required="true"
                  value={this.state.name}
                  onChange={this.onChange}
                />

                <button id="cinemas-form-submit-button" type="submit">Add</button>
              </form>
            </div>
          </div>
        );
    }
}
