import React, { Component } from 'react';
import Navbar from '../Navbar/Navbar';

class Layout extends Component {

    render() {
        return (
            <React.Fragment>
                <Navbar></Navbar>
                <main className="content">
                    {this.props.children}
                </main>
            </React.Fragment>
        );
    }
}

export default Layout;