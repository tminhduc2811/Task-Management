import React, { Component } from "react";
import ProjectForm from "../Forms/ProjectForm";
import { Formik, Field, ErrorMessage, Form } from "formik";
import handleResponse from "../../helpers/handle-authorization";
import * as Yup from "yup";
import axios from "../../axios";

class TaskModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            task: props.task,
            action: props.action,
            apiURL: props.apiURL
        }
    }

    componentWillReceiveProps(props) {
        console.log('neww', props)
        this.setState({...this.state,
            task: props.task,
            action: props.action
        })
        console.log('aaaa', this.state)
    }

  render() {
    return (
      <div
        className="modal fade"
        id="staticBackdropTask"
        data-backdrop="static"
        data-keyboard="false"
        tabIndex="-1"
        role="dialog"
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h3 className="modal-title" id="staticBackdropLabel">
                Create new task
              </h3>
              <button
                type="button"
                className="close"
                data-dismiss="modal"
                aria-label="Close"
              >
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div className="modal-body">
              <Formik
                enableReinitialize="true"
                initialValues={{
                  summary: this.state.task.summary,
                  acceptanceCriteria: this.state.task.acceptanceCriteria,
                  status: this.state.task.status,
                  priority: this.state.task.priority,
                  dueDate: this.state.task.dueDate
                }}
                validationSchema={Yup.object().shape({
                    summary: Yup.string().required(
                    "Summary is required"
                  ),
                  acceptanceCriteria: Yup.string().required(
                    "Acceptance criteria is required"
                  ),
                  status: Yup.string().required("Status is required"),
                  priority: Yup.string().required("Priority date is required"),
                  dueDate: Yup.string().required("Due date is required"),
                })}
                onSubmit={({summary, acceptanceCriteria, status, priority, dueDate},
                  { setStatus, setSubmitting, setErrors }
                ) => {
                  setStatus();
                  setSubmitting(true)
                  console.log('ready')
                  if (this.state.action == 'add') {
                    axios
                    .post(this.state.apiURL, {
                        summary,
                        acceptanceCriteria,
                        status,
                        priority,
                        dueDate
                    })
                    .then(
                      () => {
                        setSubmitting(false);
                        window.location.reload(false);
                      }, error => {
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
                  } else {
                      // Update task

                      axios.put(this.state.apiURL + '/'  + this.state.task.sequence, {
                        summary,
                        acceptanceCriteria,
                        status,
                        priority,
                        dueDate
                    }).then(() => {
                        setSubmitting(false);
                        window.location.reload(false);
                    }, error => {
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
                    })
                  }
                }}
                render={({ errors, status, touched, isSubmitting }) => (
                  <Form>
                    <div className="form-group">
                      <label htmlFor="summary">
                        Summary
                      </label>
                      <Field
                        name="summary"
                        type="text"
                        className={
                          "form-control" +
                          (errors.summary && touched.summary
                            ? " is-invalid"
                            : "")
                        }
                      />
                      <ErrorMessage
                        name="summary"
                        component="div"
                        className="invalid-feedback"
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="acceptanceCriteria">Acceptance criteria</label>
                      <Field
                        name="acceptanceCriteria"
                        type="text"
                        className={
                          "form-control" +
                          (errors.acceptanceCriteria && touched.acceptanceCriteria
                            ? " is-invalid"
                            : "")
                        }
                      />
                      <ErrorMessage
                        name="acceptanceCriteria"
                        component="div"
                        className="invalid-feedback"
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="status">Status</label>
                      <Field
                        name="status"
                        type="text"
                        className={
                          "form-control" +
                          (errors.status && touched.status
                            ? " is-invalid"
                            : "")
                        }
                      />
                      <ErrorMessage
                        name="status"
                        component="div"
                        className="invalid-feedback"
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="priority">Priority</label>
                      <Field
                        name="priority"
                        type="text"
                        className={
                          "form-control" +
                          (errors.priority && touched.priority
                            ? " is-invalid"
                            : "")
                        }
                      />
                      <ErrorMessage
                        name="priority"
                        component="div"
                        className="invalid-feedback"
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="dueDate">Due date</label>
                      <Field
                        name="dueDate"
                        type="text"
                        className={
                          "form-control" +
                          (errors.dueDate && touched.dueDate
                            ? " is-invalid"
                            : "")
                        }
                      />
                      <ErrorMessage
                        name="dueDate"
                        component="div"
                        className="invalid-feedback"
                      />
                    </div>
                    <div className="form-group btn-form">
                      <button
                        type="submit"
                        className="btn btn-primary"
                        disabled={isSubmitting}
                      >
                        Submit
                      </button>
                      <button
                        type="button"
                        className="btn btn-danger"
                        data-dismiss="modal"
                      >
                        Close
                      </button>
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
export default TaskModal;
