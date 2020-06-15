import React from "react";
import { Formik, Field, ErrorMessage, Form } from "formik";
import './ProjectForm.css';

const ProjectForm = (props) => {
  const errors = props.errors;
  const touched = props.touched;
  const status = props.status;
  const isSubmitting = props.isSubmitting;

  return (
    <Form>
      <div className="form-group">
        <label htmlFor="projectIdentifier">Project Identifier</label>
        <Field name="projectIdentifier" type="text" className={"form-control" + (errors.projectIdentifier && touched.projectIdentifier? " is-invalid" : "")} />
        <ErrorMessage name="projectIdentifier" component="div" className="invalid-feedback" />
      </div>
      <div className="form-group">
        <label htmlFor="projectName">Project Name</label>
        <Field name="projectName" type="projectName" className={ "form-control" + (errors.projectName && touched.projectName ? " is-invalid" : "")} />
        <ErrorMessage name="projectName" component="div" className="invalid-feedback" />
      </div>
      <div className="form-group">
        <label htmlFor="description">Description</label>
        <Field name="description" type="description" className={"form-control" + (errors.description && touched.description ? " is-invalid" : "")} />
        <ErrorMessage name="description" component="div"className="invalid-feedback" />
      </div>
      <div className="form-group">
        <label htmlFor="startDate">Start date</label>
        <Field
          name="startDate" type="startDate" className={"form-control" + (errors.startDate && touched.startDate ? " is-invalid" : "")} />
        <ErrorMessage name="startDate" component="div" className="invalid-feedback" />
      </div>
      <div className="form-group">
        <label htmlFor="endDate">End date</label>
        <Field name="endDate" type="endDate" className={"form-control" + (errors.endDate && touched.endDate ? " is-invalid" : "")} />
        <ErrorMessage name="endDate" component="div" className="invalid-feedback" />
      </div>
      <div className="form-group btn-form">
        <button type="submit" className="btn btn-primary" disabled={isSubmitting}> Submit</button>
        <button type="button" className="btn btn-danger" data-dismiss="modal">Close</button>
        {isSubmitting && (
          <img alt="" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
        )}
      </div>
      {status && <div className={"alert alert-danger"}>{status}</div>}
    </Form>
  );
};

export default ProjectForm;
