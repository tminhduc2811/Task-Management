import React from 'react';
import ProjectForm from '../Forms/ProjectForm';
import { Formik, Field, ErrorMessage, Form } from "formik";
import handleResponse from '../../helpers/handle-authorization';
import * as Yup from "yup";
import axios from '../../axios';

const ProjecModal = (props) => {
    return(
        <div
          className="modal fade"
          id="staticBackdrop"
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
                  Create Project
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
                  initialValues={{
                    projectIdentifier: "",
                    projectName: "",
                    description: "",
                    startDate: "",
                    endDate: "",
                  }}
                  validationSchema={Yup.object().shape({
                    projectIdentifier: Yup.string().required(
                      "Project Identifier is required"
                    ),
                    projectName: Yup.string().required(
                      "Project name is required"
                    ),
                    description: Yup.string().required(
                      "Description is required"
                    ),
                    startDate: Yup.string().required("Start date is required"),
                    endDate: Yup.string().required("End date is required"),
                  })}
                  onSubmit={(
                    {
                      projectIdentifier,
                      projectName,
                      description,
                      startDate,
                      endDate,
                    },
                    { setStatus, setSubmitting, setErrors }
                  ) => {
                    setStatus();
                    axios
                      .post("/projects", {
                        projectIdentifier: projectIdentifier,
                        projectName: projectName,
                        description: description,
                        startDate: startDate,
                        endDate: endDate,
                      })
                      .then(
                        (rs) => {
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
                    <ProjectForm
                      errors={errors}
                      status={status}
                      touched={touched}
                      isSubmitting={isSubmitting}
                    />
                  )}
                />
              </div>
            </div>
          </div>
        </div>
    );
}
export default ProjecModal;