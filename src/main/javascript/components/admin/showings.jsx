import * as React from 'react';
import moment from 'moment';
import LoadingSpinner from '../loadingSpinner';

export default class Showings extends React.Component {
    static momentizeShowing(showing) {
        return Object.assign({}, showing, { time: moment(showing.time) });
    }

    constructor(props) {
        super(props);
        this.state = {
            cinemas: null,
            showings: null,
            films: null,
            selectedScreen: null,
            date: '',
            time: '',
            film: '',
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        this.props.api.get('/api/cinemas')
            .then(data => this.setState({ cinemas: data }))
            .catch(error => this.props.errorHandler.onUnrecoverableError(error));

        this.props.api.get('/api/admin/showings')
            .then(data => this.setState({
                showings: data.map(Showings.momentizeShowing),
            }))
            .catch(error => this.props.errorHandler.onUnrecoverableError(error));

        this.props.api.get('/api/admin/films')
            .then(data => this.setState({ films: data }))
            .catch(error => this.props.errorHandler.onUnrecoverableError(error));
    }

    onChange(event) {
        this.setState({ [event.target.name]: event.target.value });
    }

    onSubmit(event) {
        event.preventDefault();
        const datetime = moment(`${this.state.date} - ${this.state.time}`, 'YYYY/MM/DD - HH:mm');

        this.props.api.post('/api/admin/showings', {
            screen: {
                id: this.state.selectedScreen.id,
            },
            film: {
                id: this.state.film,
            },
            time: datetime.toISOString(),
        })
            .then(showing => this.setState({
                showings: this.state.showings.concat([Showings.momentizeShowing(showing)]),
            }))
            .catch(error => this.props.errorHandler.onRecoverableError(`Unable to create showing: ${error.message}`));
    }

    onDelete(id) {
        this.props.api.del(`/api/admin/showings/${id}`)
            .then(() => this.setState({
                showings: this.state.showings.filter(showing => showing.id !== id),
            }))
            .catch(error => this.props.errorHandler.onRecoverableError(`Unable to delete showing: ${error.message}`));
    }

    selectScreen(cinema, screen) {
        this.setState({
            selectedScreen: screen,
            selectedCinema: cinema,
        });
    }

    isLoaded() {
        return this.state.cinemas !== null &&
            this.state.showings !== null &&
            this.state.films !== null;
    }

    render() {
        if (!this.isLoaded()) {
            return <LoadingSpinner />;
        }

        return (
          <div id="showings" className="admin-showings">
            {this.renderScreenSelector()}
            {this.state.selectedScreen &&
                <div className="showings-details">
                    <h3>{this.state.selectedCinema.name} - {this.state.selectedScreen.name}</h3>
                    {this.renderExistingShowings()}
                    {this.renderNewShowingForm()}
                </div>}
          </div>
        );
    }

    renderScreenSelector() {
        return this.state.cinemas.length
            ? <div id="showings-screen-selector" className="screen-selector">
                {this.state.cinemas.map(cinema =>
                    cinema.screens.map(screen => (
                        <button
                            className={this.state.selectedScreen === screen ? "selected" : null}
                            disabled={this.state.selectedScreen === screen}
                            key={screen.id}
                            onClick={() => this.selectScreen(cinema, screen)}>
                            {cinema.name} - {screen.name}
                        </button>
                    )))}
            </div>
            : <div>No screens available!</div>;
    }

    renderExistingShowings() {
        return (
            <div className="showings-list">
                <table>
                    <thead>
                    <tr>
                        <th>Film</th>
                        <th style={{ width: '15%' }}>date / time</th>
                        <th style={{ width: '1rem' }} />
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.showings
                            .filter(showing => showing.screen.id === this.state.selectedScreen.id)
                            .map(showing => (
                                <tr key={showing.id}>
                                    <td className="showing-detail-film-name">{showing.film.name}</td>
                                    <td className="showing-detail-time">{showing.time.format('D/M/YYYY HH:mm')}</td>
                                    <td>
                                        <button
                                            aria-label="Delete button"
                                            className="showing-detail-delete-button delete-button"
                                            onClick={() => this.onDelete(showing.id)}
                                        />
                                    </td>
                                </tr>))
                    }
                    </tbody>
                </table>
            </div>
        );
    }

    renderNewShowingForm() {
        return (
            <div className="centered-form-container">
                <h2>New Showing</h2>
                <form onSubmit={this.onSubmit}>

                    <label htmlFor="showing-form-film-field">Film</label>
                    <select
                        id="showing-form-film-field"
                        value={this.state.form}
                        name="film"
                        onChange={this.onChange}
                        required="true"
                        defaultValue="not-selected">
                        <option
                            disabled="true"
                            value="not-selected">
                            Please select a film
                        </option>
                        {this.state.films.map(film => (
                            <option key={film.id} value={film.id}>{film.name}</option>
                        ))}
                    </select>

                    <label htmlFor="showing-form-date-field">Date</label>
                    <input
                        id="showing-form-date-field"
                        type="date"
                        value={this.state.date}
                        name="date"
                        onChange={this.onChange}
                        required="true"/>

                    <label htmlFor="showing-form-time-field">Time (HH:mm)</label>
                    <input
                        id="showing-form-time-field"
                        type="text"
                        value={this.state.time}
                        name="time"
                        onChange={this.onChange}
                        required="true"/>

                    <button id="showing-form-submit-button" type="submit">Add</button>
                </form>
            </div>
        );
    }
}
