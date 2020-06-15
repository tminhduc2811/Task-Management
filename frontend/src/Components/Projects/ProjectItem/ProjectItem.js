import React from "react";
import "./ProjectItem.css";
import { Link } from "react-router-dom";

const ProjectItem = (props) => {
  return (
    <div className="container-fluid project-wrapper">
      <div className="row">
        <div className="col-md-3 text-left">
          <h3>{props.project.projectIdentifier}</h3>
        </div>
        <div className="col-md-7 text-left">
          <div className="row">
            <h3>{props.project.projectName}</h3>
          </div>
          <div className="row">{props.project.description}</div>
        </div>
        <div className="col-md-2 text-left">
          <Link to={"projects/" + props.project.projectIdentifier}>
            <button
              type="button"
              className="btn btn-info btn-dashboard"
            >Project Board</button>
          </Link>

          <button
            type="button"
            className="btn btn-success btn-dashboard"
            data-toggle="modal"
            data-target="#staticBackdropUpdate"
            onClick={() => props.loadModal(props.project.projectIdentifier)}
          >
            Update project
          </button>
          <button
            type="button"
            className="btn btn-danger btn-dashboard"
            onClick={() => props.deleteProject(props.project.projectIdentifier)}
          >
            Delete project
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProjectItem;
