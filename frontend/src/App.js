import React, { Component } from "react";
import "./App.css";
import LoginPage from "./Containers/LoginPage/LoginPage";
import { authenticationService } from "./Services/AuthenticationService";
import { history } from "./helpers/history";
import { Router, Route, Switch } from "react-router-dom";
import { PrivateRoute } from "./Components/PrivateRoute";
import Dashboard from "./Containers/Dashboard/Dashboard";
import Projects from "./Components/Projects/Projects";
import Layout from "./Components/Layout/Layout";

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
  }

  render() {
    const authInfo = this.state.authInfo;

    return (
      <Router history={history}>
        <PrivateRoute exact path="/" component={defaultContainer} />
        <Route path="/login" component={LoginPage} />
      </Router>
    );
  }
}

const defaultContainer = () => (
  <div className="app">
    <Layout>
      <Route path="/"  component={Dashboard} />
      <Route path="/" component={Projects}/>
    </Layout>
  </div>
);

export default App;
