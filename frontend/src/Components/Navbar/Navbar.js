import React from 'react';
import './Navbar.css';

const Navbar = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-primary">
            <a className="navbar-brand" href>Project Management Tool</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li className="nav-item">
                        <a className="nav-item" href>Dashboard</a>
                    </li>
                </ul>
                <ul className="navbar-nav ml-auto mt-2 mt-lg-0">
                    <li>
                        <a className="nav-item" href>Sign Up</a>
                    </li>
                    <li>
                        <a className="nav-item" href>Login</a>
                    </li>
                </ul>
            </div>
        </nav>
    );
}

export default Navbar;