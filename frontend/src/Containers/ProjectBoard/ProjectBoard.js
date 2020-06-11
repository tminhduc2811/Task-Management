import React, { Component } from "react";
import "./ProjectBoard.css";
import Task from "../../Components/Task/Task";
class ProjectBoard extends Component {
  render() {
    console.log(this.props.match.params.projectIdentifier);
    return (
      <div className="container-fluid wrapper">
        <div className="row">
        <button
          type="button"
          className="btn btn-success"
          data-toggle="modal"
          data-target="#staticBackdrop"
          style={{margin: '15px'}}
        >New task</button>
        </div>
        <div className="row">
          <div className="col-md-4">
            <div className="header-task">
              <div className="btn btn-success w-100 tasks-header">TO DO</div>
            </div>
            <Task></Task>
          </div>
          <div className="col-md-4">
            <div className="header-task">
              <div className="btn btn-info w-100 tasks-header">IN PROGRESS</div>
            </div>
          </div>
          <div className="col-md-4">
            <div className="header-task">
              <div className="btn btn-danger w-100 tasks-header">DONE</div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default ProjectBoard;
