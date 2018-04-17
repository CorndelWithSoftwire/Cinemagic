import * as React from 'react';
import LoadingSpinner from '../loadingSpinner';

export default class Films extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            films: null,
            name: '',
            lengthMinutes: '',
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        this.props.api.get('/api/admin/films')
            .then(data => this.setState({ films: data }))
            // Error while mounting is not recoverable
            .catch(error => this.props.errorHandler.onUnrecoverableError(error));
    }

    onChange(event) {
        this.setState({ [event.target.name]: event.target.value });
    }

    onClickDeleteFilm(id) {
        this.props.api.del(`/api/admin/films/${id}`)
            .then(() => this.setState({ films: this.state.films.filter(film => film.id !== id) }))
            .catch(error => this.props.errorHandler.onRecoverableError(`Unable to delete film: ${error.message}`));
    }

    onSubmit(event) {
        event.preventDefault();
        this.props.api.post('/api/admin/films', {
            name: this.state.name,
            lengthMinutes: this.state.lengthMinutes,
        })
            .then((data) => {
                this.setState({
                    films: this.state.films.concat([data]),
                    name: '',
                    lengthMinutes: '',
                });
            })
            .catch(error => this.props.errorHandler.onRecoverableError(`Unable to create film: ${error.message}`));
    }

    isLoaded() {
        return this.state.films;
    }

    render() {
        if (this.isLoaded()) {
            return (
              <div>
                <h2 id="films-title"> Films </h2>
                <table>
                  <thead>
                    <tr>
                      <th>Name</th>
                      <th style={{ width: '15%' }}>Length (minutes)</th>
                      <th style={{ width: '1rem' }} />
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.films.map(film => (
                      <tr key={film.id}>
                        <td className="films-details-name">{film.name}</td>
                        <td className="films-details-length-minutes">{film.lengthMinutes}</td>
                        <td>
                          <button
                            className="films-details-delete-button delete fas fa-minus-circle pull-right"
                            key={film.id}
                            onClick={() => this.onClickDeleteFilm(film.id)}
                          />
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
                <div className="centered-form-container">
                  <h2> Add new film </h2>
                  <form onSubmit={this.onSubmit}>
                    <label htmlFor="films-form-name-field">Name
                    </label>

                    <input
                      id="films-form-name-field"
                      type="text"
                      name="name"
                      required="true"
                      value={this.state.name}
                      onChange={this.onChange}
                    />

                    <label htmlFor="films-form-length-minutes-field">Length (minutes)

                    </label>
                    <input
                      id="films-form-length-minutes-field"
                      type="text"
                      name="lengthMinutes"
                      required="true"
                      value={this.state.lengthMinutes}
                      onChange={this.onChange}
                    />
                    <button
                      id="films-form-submit-button"
                      type="submit"
                    >
                                Add
                    </button>
                  </form>
                </div>
              </div>);
        }
        return <LoadingSpinner />;
    }
}
