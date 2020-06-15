import React, { Component } from "react";
import ProjectForm from "../Forms/ProjectForm";
import { Formik, Field, ErrorMessage, Form } from "formik";
import handleResponse from "../../helpers/handle-authorization";
import * as Yup from "yup";
import axios from "../../axios";

class ProjecUpdateModal extends Component {
  constructor(props) {
    super(props);

    this.state = {
      project: props.project,
      action: props.action,
      keyValue: '',
      prevKey: ''
    }
  }

  componentWillReceiveProps(props) {
    console.log('neww', props)
    const prevKey = this.state.keyValue
    this.setState({
      ...this.state,
      project: props.project,
      action: props.action,
      prevKey: prevKey,
      keyValue: props.keyValue,
    })
    console.log('aaaa', this.state)
  }

  handleOnChange(e) {
    this.setState({
      ...this.state,
      project: { ...this.state.project, [e.target.name]: e.target.value }
    })
  }
  render() {
    return (
      <div
        className="modal fade" id="staticBackdropUpdate" data-backdrop="static" data-keyboard="false" tabIndex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h3 className="modal-title" id="staticBackdropLabel">
                Create Project
              </h3>
              <button type="button" className="close" data-dismiss="modal" aria-label="Close" >
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div className="modal-body">
              <Formik
                enableReinitialize="true"
                initialValues={{
                  projectIdentifier: this.state.project.projectIdentifier,
                  projectName: this.state.project.projectName,
                  description: this.state.project.description,
                  startDate: this.state.project.startDate,
                  endDate: this.state.project.endDate,
                }}
                validationSchema={Yup.object().shape({
                  projectIdentifier: Yup.string().required(
                    "Project Identifier is required"
                  ),
                  projectName: Yup.string().required(
                    "Project name is required"
                  ),
                  description: Yup.string().required("Description is required"),
                })}
                onSubmit={({ projectIdentifier, projectName, description, startDate, endDate }, { setStatus, setSubmitting, setErrors }) => {
                  setStatus();
                  console.log('ready')
                  axios
                    .put("/projects/" + projectIdentifier, {
                      projectName,
                      description,
                      startDate,
                      endDate,
                    })
                    .then(
                      () => {
                        setSubmitting(false);
                        window.location.reload(false);
                      },
                      (error) => {
                        handleResponse(error);
                        setSubmitting(false);
                        console.log("error ", error.response);
                        const message = error.response.data.details;

                        if (message == "Validation failed") {
                          // setStatus(error.response.data.errors)
                          setErrors(error.response.data.errors);
                        } else {
                          setStatus(message);
                        }
                      }
                    );
                }}
                render={({ errors, status, touched, isSubmitting }) => (
                  <Form>
                    <div className="form-group">
                      <label htmlFor="projectIdentifier">
                        Project Identifier
                      </label>
                      <Field name="projectIdentifier" type="text" className={"form-control" + (errors.projectIdentifier && touched.projectIdentifier ? " is-invalid" : "")} disabled={true} />
                      <ErrorMessage name="projectIdentifier" component="div" className="invalid-feedback" />
                    </div>
                    <div className="form-group">
                      <label htmlFor="projectName">Project Name</label>
                      <Field name="projectName" type="projectName" className={"form-control" + (errors.projectName && touched.projectName ? " is-invalid" : "")} />
                      <ErrorMessage name="projectName" component="div" className="invalid-feedback" />
                    </div>
                    <div className="form-group">
                      <label htmlFor="description">Description</label>
                      <Field name="description" type="description" className={"form-control" + (errors.description && touched.description ? " is-invalid" : "")} />
                      <ErrorMessage name="description" component="div" className="invalid-feedback" />
                    </div>
                    <div className="form-group">
                      <label htmlFor="startDate">Start date</label>
                      <Field name="startDate" type="startDate" className={"form-control" + (errors.startDate && touched.startDate ? " is-invalid" : "")} />
                      <ErrorMessage name="startDate" component="div" className="invalid-feedback" />
                    </div>
                    <div className="form-group">
                      <label htmlFor="endDate">End date</label>
                      <Field name="endDate" type="endDate" className={"form-control" + (errors.endDate && touched.endDate ? " is-invalid" : "")} />
                      <ErrorMessage name="endDate" component="div" className="invalid-feedback" />
                    </div>
                    <div className="form-group btn-form">
                      <button type="submit" className="btn btn-primary" disabled={isSubmitting}>Submit </button>
                      <button type="button" className="btn btn-danger" data-dismiss="modal" >Close</button>
                      {isSubmitting && (
                        <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                      )}
                    </div>
                    {status && (
                      <div className={"alert alert-danger"}>{status}</div>
                    )}
                  </Form>
                )}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default ProjecUpdateModal;
