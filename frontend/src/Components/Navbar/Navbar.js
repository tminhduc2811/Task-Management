import React from 'react';
import './Navbar.css';
import { NavLink } from 'react-router-dom';
import { authenticationService } from '../../Services/AuthenticationService';
import { history } from '../../utils/history';

const Navbar = () => {

    const logout = () => {
        authenticationService.logout();
        history.push("/login");
    }
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
            <NavLink className="navbar-brand" to="/">Task Management Tool</NavLink>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/">Dashboard</NavLink>
                    </li>
                </ul>
                <ul className="navbar-nav ml-auto mt-2 mt-lg-0">
                    <li>
                        <a className="nav-item nav-link" onClick={logout}>Log out</a>
                    </li>
                </ul>
            </div>
        </nav>
    );
}

export default Navbar;