import React from "react";

const Task = (props) => {
  return (
    <div>
      <div className="card">
        <div className="card-header text-white bg-success mb-3">Header</div>
        <div className="card-body">
          <h5 className="card-title">Success card title</h5>
          <p className="card-text">
            Some quick example text to build on the card title and make up the
            bulk of the card's content.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Task;
