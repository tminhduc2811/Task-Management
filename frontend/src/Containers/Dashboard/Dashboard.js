import React, { Component } from "react";
import Projects from '../../Components/Projects/Projects';

class Dashboard extends Component {
    render() {
        return(
            <div className="container-fluid">
                <h1>Projects</h1>
                <button type="button" className="btn btn-success" data-toggle="modal" data-target="#staticBackdrop">Create a project</button>
                <Projects></Projects>
                // modal
            <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="staticBackdropLabel">Create Project</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                <div class="modal-body">
                    ...implement later
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-success">Submit</button>
                </div>
                </div>
            </div>
            </div>
            </div>
        );
    }
}

export default Dashboard;

