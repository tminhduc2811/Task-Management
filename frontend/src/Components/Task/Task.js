import React from "react";

const Task = (props) => {
  const itemClass = "card-header text-white mb-3 " + (props.task.priority === 0 ? "bg-success" : props.task.priority === 1 ? "bg-warning": "bg-danger");
  const priority = props.task.priority === 0 ? "LOW" : (props.task.priority === 1 ? "MEDIUM" : "HIGH")
  return (
    <div>
      <div className="card">
        <div className={itemClass}>
          ID: {props.task.sequence} - Priority: {priority}
        </div>
        <div className="card-body">
          <h5 className="card-title">{props.task.summary}</h5>
          <p className="card-text">{props.task.acceptanceCriteria}</p>
          <p className="card-text">Due date: {props.task.dueDate}</p>
          <button
            type="button"
            className="btn btn-primary"
            style={{ maxWidth: "120px", padding: "8px", marginRight: "10px", cursor: 'pointer' }}
            data-toggle="modal"
            data-target="#staticBackdropTask"
            onClick={() => props.onUpdate(props.task)}
          >
            View/Update
          </button>
          <button
            type="button"
            className="btn btn-danger"
            style={{ maxWidth: "80px", padding: "8px", cursor: 'pointer' }}
            onClick={() => props.onDelete(props.task.sequence)}
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
};

export default Task;
