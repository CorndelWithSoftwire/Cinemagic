import * as React from 'react';

export default class Screens extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            screens: this.props.screens,
            name: '',
            rows: 10,
            rowWidth: 10,
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onClickDeleteScreen(id) {
        this.props.api.del(`/api/admin/screens/${id}`)
            .then(() => this.setState({
                screens: this.state.screens.filter(screen => screen.id !== id),
            }))
            .catch(error => this.props.errorHandler.onRecoverableError(`Unable to delete screen: ${error.message}`));
    }

    onChange(event) {
        this.setState({ [event.target.name]: event.target.value });
    }

    onSubmit(event) {
        event.preventDefault();
        this.props.api.post('/api/admin/screens', {
            rows: this.state.rows,
            rowWidth: this.state.rowWidth,
            name: this.state.name,
            cinema: { id: this.props.cinemaId },
        })
            .then(data => this.setState({ screens: this.state.screens.concat([data]) }))
            .catch(error => this.props.errorHandler.onRecoverableError(`Unable to create screen: ${error.message}`));
    }

    render() {
        return (
          <div>
            <table>
              <thead>
                <tr>
                  <th>Screen name</th>
                  <th style={{ width: '15%' }}>Screen rows</th>
                  <th style={{ width: '15%' }}>Screen row width</th>
                  <th style={{ width: '1rem' }} />
                </tr>
              </thead>
              <tbody>
                {this.state.screens.map(screen => (
                  <tr key={screen.id}>
                    <td className="screen-detail-name">{screen.name}</td>
                    <td className="screen-detail-rows">{screen.rows}</td>
                    <td className="screen-detail-row-width">{screen.rowWidth}</td>
                    <td>
                      <button
                        aria-label="Delete screen"
                        className="screen-detail-delete-button delete-button"
                        onClick={() => this.onClickDeleteScreen(screen.id)}
                      />
                    </td>
                  </tr>
                        ))}
              </tbody>
            </table>

            <div className="centered-form-container">
              <h3>Add new Screen</h3>
              <form onSubmit={this.onSubmit}>
                <label htmlFor="screen-form-name-field">Name</label>
                <input
                  className="screen-form-name-field"
                  type="text"
                  value={this.state.name}
                  name="name"
                  onChange={this.onChange}
                  required="true"
                />

                <label htmlFor="screen-form-rows-field">Rows</label>
                <input
                  className="screen-form-rows-field"
                  type="text"
                  value={this.state.rows}
                  name="rows"
                  onChange={this.onChange}
                  required="true"
                />

                <label htmlFor="screen-form-row-width-field">Row Width</label>
                <input
                  className="screen-form-row-width-field"
                  type="text"
                  value={this.state.rowWidth}
                  name="rowWidth"
                  onChange={this.onChange}
                  required="true"
                />

                <button
                  className="screen-form-submit-button"
                  type="submit"
                >
                            Add
                </button>
              </form>
            </div>
          </div>
        );
    }
}
