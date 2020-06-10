package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.dto.ProjectCreateDto
import com.ducta.taskmanagement.dto.ProjectDto
import com.ducta.taskmanagement.dto.ProjectUpdateDto

interface ProjectService {
    fun createProject(username: String, projectCreateDto: ProjectCreateDto)

    fun getAllProjectsByUsername(username: String): List<ProjectDto>

    fun getProjectByProjectIdentifier(projectIdentifier: String): ProjectDto

    fun deleteProject(projectIdentifier: String)

    fun updateProject(projectIdentifier: String, projectUpdateDto: ProjectUpdateDto)

    fun isUserOwnerOfProject(projectIdentifier: String, username: String)
}