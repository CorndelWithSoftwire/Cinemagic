import * as React from "react";
import moment from "moment";

/**
 * Component for selecting a Film and Time after the cinema has been selected
 */
export default class FilmTimeSelect extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showings: []
        };
    }

    componentDidMount() {
        this.loadShowings();
    }

    render() {
        return (
            <div className="film-time-select">
                <h3>{this.props.cinema.name}</h3>
                <div>
                    {this.state.showings.map((s, index) =>
                        <button key={index} onClick={() => this.props.selectShowing(s)}>
                            {s.film.name} - {s.time.format("MMM Do h:mm a")}
                        </button>
                    )}
                </div>
            </div>
        );
    }

    loadShowings() {
        fetch("/api/cinemas/" + encodeURIComponent(this.props.cinema.id) + "/showings").then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                // TODO: errors
                throw new Error("Error response fetching showings", response);
            }
        }).then(showings => {
            showings.forEach(s => {
                // momentise the timestamps
                s.time = moment(s.time);
            });
            this.setState({ showings: showings });
        });
    }
}
