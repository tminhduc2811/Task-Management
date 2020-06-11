import React, { Component } from "react";
import Projects from "../../Components/Projects/Projects";
import axios from "../../axios";
import { Formik, Field, ErrorMessage, Form } from "formik";
import { authenticationService } from "../../Services/AuthenticationService";
import * as Yup from "yup";
import { history } from "../../helpers/history";
import ProjectForm from "../../Components/Forms/ProjectForm";
import handleResponse from "../../helpers/handle-authorization";
import ProjecModal from "../../Components/Modal/ProjectModal";

class Dashboard extends Component {
  state = {
    isLoaded: false,
    projects: [],
  };

  componentDidMount() {
    this.getProject();
  }

  getProject() {
    axios.get("/projects").then((response) => {
      console.log("fetch rs ", response);
      this.setState({
        ...this.state,
        isLoaded: true,
        projects: response.data ? response.data : [],
      });
    }, error => {
        handleResponse(error);
    });
  }

  render() {
    const deleteProject = (projectIdentifier) => {
      if (this.state.projects) {
        axios.delete("/projects/" + projectIdentifier).then((rs) => {
          this.getProject();
        });
      }
    };

    return (
      <div className="container-fluid">
        <h1>Projects</h1>
        <button
          type="button"
          className="btn btn-success"
          data-toggle="modal"
          data-target="#staticBackdrop"
        >
          Create a project
        </button>
        {this.state.isLoaded && (
          <Projects
            projects={this.state.projects}
            deleteProject={deleteProject}
          ></Projects>
        )}
        <ProjecModal></ProjecModal>
      </div>
    );
  }
}

export default Dashboard;
