import React from 'react';
import {Link} from 'react-router-dom';

export default class AdminNavBar extends React.Component {
    render() {
        return (
            <nav className="admin-navbar">
                <ul>
                    <li className="navbar-title"><h1>Administration</h1></li>
                    <li className="navbar-link"><Link to={`/admin/cinemas`} id="admin-link-cinemas">Cinemas</Link></li>
                    <li className="navbar-link"><Link to={`/admin/films`} id="admin-link-films">Films</Link></li>
                    <li className="navbar-link"><Link to={`/admin/showings`} id="admin-link-showings">Showings</Link></li>
                </ul>
            </nav>
        );
    }
}
