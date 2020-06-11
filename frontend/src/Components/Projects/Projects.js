import React from "react";
import ProjectItem from "./ProjectItem/ProjectItem";

const Projects = (props) => {
  return (
    <div>
      {props.projects.map((project, index) => {
        return (
          <ProjectItem
            project={project}
            key={project.projectIdentifier}
            deleteProject={props.deleteProject}
          ></ProjectItem>
        );
      })}
    </div>
  );
};

export default Projects;
