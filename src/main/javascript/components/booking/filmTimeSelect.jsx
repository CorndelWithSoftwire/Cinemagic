import * as React from 'react';
import moment from 'moment';
import LoadingSpinner from '../loadingSpinner';

/**
 * Component for selecting a Film and Time after the cinema has been selected
 */
export default class FilmTimeSelect extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showings: null,
        };
    }

    componentDidMount() {
        this.props.api.get(`/api/cinemas/${encodeURIComponent(this.props.cinema.id)}/showings`).then((showings) => {
            // momentise the timestamps
            showings.forEach((showing) => {
                showing.time = moment(showing.time); // eslint-disable-line no-param-reassign
            });
            this.setState({ showings });
        }).catch(error => this.props.errorHandler.onUnrecoverableError(error));
    }

    isLoaded() {
        return this.state.showings !== null;
    }

    render() {
        const showings = () => {
            if (this.state.showings.length) {
                return (
                  <div>
                    {this.state.showings.map(s => (
                      <button
                        key={s.film.id}
                        onClick={() => this.props.selectShowing(s)}
                        className="film-selection-button"
                      >
                        {s.film.name} - {s.time.format('MMM Do h:mm A')}
                      </button>))
                        }
                  </div>
                );
            }
            return <p>There are no showings for this cinema.</p>;
        };

        if (!this.isLoaded()) {
            return <LoadingSpinner />;
        }
            return (
              <div className="film-time-select">
                <h1>Select a showing</h1>
                <h3>Cinema: {this.props.cinema.name}</h3>
                {showings()}
              </div>
            );
    }
}
