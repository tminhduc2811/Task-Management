import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import { authenticationService } from '../Services/AuthenticationService';

export const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => {
        const authInfo = authenticationService.authInfoValue;
        console.log('auth ', authenticationService.authInfoValue)
        if (!authInfo) {
            // not logged in so redirect to login page with the return url
            return <Redirect to="/login" />
        }

        // authorised so return component
        return <Component {...props} />
    }} />
)