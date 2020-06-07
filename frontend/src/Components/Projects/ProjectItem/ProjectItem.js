import React from "react";
import './ProjectItem.css';

const ProjectItem = (props) => {
  return (
    <div className="container-fluid project-wrapper">
      <div className="row">
        <div className="col-md-3 text-left">
            Project identifier
        </div>
        <div className="col-md-6 text-left">
            Project name
        </div>
        <div className="col-md-3 text-left">
            bla bla
        </div>
      </div>
    </div>
  );
}

export default ProjectItem;
