import * as React from 'react';
import LoadingSpinner from '../loadingSpinner';

/**
 * Initial cinema selection form before anything else has been chosen
 */
export default class CinemaSelect extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selected: null,
            cinemas: null,
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    componentDidMount() {
        this.props.api.get('/api/cinemas')
            .then(data => this.setState({ cinemas: data }))
            .catch(error => this.props.errorHandler.onUnrecoverableError(error));
    }

    onChange(event) {
        this.setState({ selected: this.state.cinemas[event.target.value] });
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.selectCinema(this.state.selected);
    }

    isLoaded() {
        return this.state.cinemas !== null;
    }

    render() {
        if (this.isLoaded()) {
            return (
              <div className="cinema-select">
                <h1>Choose a Cinema</h1>
                <form className="cinema-select-form" onSubmit={this.handleSubmit}>
                  <select defaultValue="" onChange={this.onChange}>
                    <option value="" disabled>Choose a Cinema...</option>
                    {this.state.cinemas.map(({ name }, index) =>
                      <option key={name} value={index}>{name}</option>)
                            }
                  </select>
                  <button type="submit">Go!</button>
                </form>
              </div>
            );
        }
        return <LoadingSpinner />;
    }
}
