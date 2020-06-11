import React, { Component } from "react";
import "./App.css";
import LoginPage from "./Containers/LoginPage/LoginPage";
import { authenticationService } from "./Services/AuthenticationService";
import { history } from "./helpers/history";
import { Router, Route, Switch, Redirect } from "react-router-dom";
import { PrivateRoute } from "./Components/PrivateRoute";
import Dashboard from "./Containers/Dashboard/Dashboard";
import Projects from "./Components/Projects/Projects";
import Layout from "./Components/Layout/Layout";
import ProjectBoard from "./Containers/ProjectBoard/ProjectBoard";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authenticated: false,
      authInfo: null,
    };
  }

  componentDidMount() {
    authenticationService.authInfo.subscribe((data) =>
      this.setState({
        authenticated: true,
        authInfo: data,
      })
    );
  }

  logout() {
    authenticationService.logout();
    history.push("/login");
    window.location.reload(false);
  }

  render() {
    const authInfo = this.state.authInfo;

    return (
      <Router history={history}>
      <PrivateRoute path="/" component={defaultContainer} />
        <Route path="/login" component={LoginPage} />
      </Router>
    );
  }
}

const defaultContainer = () => (
  <div className="app">
    <Layout>
      <Switch>
        <Route path="/projects/:projectIdentifier" component={ProjectBoard} />
        <Route path="/" component={Dashboard} />
      </Switch>
    </Layout>
  </div>
);

export default App;
