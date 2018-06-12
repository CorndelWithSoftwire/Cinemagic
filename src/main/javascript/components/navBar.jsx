import * as React from 'react';
import {Link} from 'react-router-dom';

export default class NavBar extends React.Component {
    render() {
        return (
            <nav className="app-navbar">
                <ul>
                    <li className="title">
                        <Link to="/">Cinemagic</Link>
                        <span className="star-icon" aria-hidden="true"/>
                    </li>
                </ul>
            </nav>
        );
    }
}
