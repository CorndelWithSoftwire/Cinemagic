import * as React from "react";

/**
 * Initial cinema selection form before anything else has been chosen
 */
export default class CinemaSelect extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            selected: null,
            cinemas: []
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    componentDidMount() {
        this.loadCinemas();
    }

    render() {
        return (
            <div className="cinema-select">
                <h3>Choose a Cinema</h3>
                <form onSubmit={this.handleSubmit}>
                    <select defaultValue="" onChange={this.onChange}>
                        <option value="" disabled>Choose a Cinema...</option>
                        {this.state.cinemas.map((c, index) => <option key={index} value={index}>{c.name}</option>)}
                    </select>
                    <button type="submit">
                        Go!
                    </button>
                </form>
            </div>
        );
    }

    loadCinemas() {
        fetch("/api/cinemas").then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                // TODO proper error handling
                throw new Error("Got an error fetching cinemas", response);
            }
        }).then(data => {
            this.setState({ cinemas: data });
        });
    }

    onChange(event) {
        this.setState({ selected: this.state.cinemas[event.target.value] });
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.selectCinema(this.state.selected);
    }
}