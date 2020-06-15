import React, { Component } from "react";
import "./ProjectBoard.css";
import Task from "../../Components/Task/Task";
import axios from "../../axios";
import TaskModal from "../../Components/Modal/TaskModal";
import handleResponse from "../../utils/handle-authorization";
class ProjectBoard extends Component {
  state = {
    tasks: [],
    isLoaded: false,
    task: {
      summary: "",
      acceptanceCriteria: "",
      status: "",
      priority: "",
      dueDate: "",
    },
    action: "add",
  };

  apiURL = "/backlog/" + this.props.match.params.projectIdentifier;
  componentDidMount() {
    axios.get(this.apiURL).then((rs) => {
      this.setState({ ...this.state, tasks: rs.data ? rs.data : [] });
    }, error => {
      handleResponse(error)
    });
  }


  render() {
    const deleteTask = (sequence) => {
      axios
        .delete(this.apiURL + "/" + sequence)
        .then(() => window.location.reload(false));
    };

    const onNewTask = () => {
      this.setState({
        action: "add",
        task: {
          ...this.state.task,
          summary: "",
          acceptanceCriteria: "",
          status: "",
          priority: "",
          dueDate: "",
        },
      });
    }

    const onUpdateTask = (task) => {
      this.setState({
        action: 'update',
        task: task
      })
    }

    return (
      <div className="container-fluid wrapper">
        <div className="row">
          <button
            onClick={onNewTask}
            type="button"
            className="btn btn-success"
            data-toggle="modal"
            data-target="#staticBackdropTask"
            style={{ margin: "15px" }}
          >
            New task
          </button>
        </div>
        <div className="row">
          <div className="col-md-4">
            <div className="header-task">
              <div className="btn btn-secondary w-100 tasks-header">TO DO</div>
            </div>
            {this.state.tasks
              .filter((task) => task.status === 0)
              .map((task) => {
                return (
                  <Task
                    task={task}
                    key={task.sequence}
                    onDelete={deleteTask}
                    onUpdate={onUpdateTask}
                  ></Task>
                );
              })}
          </div>
          <div className="col-md-4">
            <div className="header-task">
              <div className="btn btn-primary w-100 tasks-header">
                IN PROGRESS
              </div>
            </div>
            {this.state.tasks
              .filter((task) => task.status === 1)
              .map((task) => {
                return (
                  <Task
                    task={task}
                    key={task.sequence}
                    onDelete={deleteTask}
                    onUpdate={onUpdateTask}
                  ></Task>
                );
              })}
          </div>
          <div className="col-md-4">
            <div className="header-task">
              <div className="btn btn-success w-100 tasks-header">DONE</div>
              {this.state.tasks
                .filter((task) => task.status === 2)
                .map((task) => {
                  return (
                    <Task
                      task={task}
                      key={task.sequence}
                      onDelete={deleteTask}
                      onUpdate={onUpdateTask}
                    ></Task>
                  );
                })}
            </div>
          </div>
        </div>
        <TaskModal
          action={this.state.action}
          apiURL={this.apiURL}
          task={this.state.task}
        ></TaskModal>
      </div>
    );
  }
}

export default ProjectBoard;
