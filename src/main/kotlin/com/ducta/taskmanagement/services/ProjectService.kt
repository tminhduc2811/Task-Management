package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.domain.User
import com.ducta.taskmanagement.domain.dto.ProjectCreateDto
import com.ducta.taskmanagement.domain.dto.ProjectDto
import com.ducta.taskmanagement.domain.dto.ProjectUpdateDto

interface ProjectService {
    fun createProject(user: User, projectCreateDto: ProjectCreateDto)

    fun getAllProjectsByUsername(username: String): List<ProjectDto>

    fun getProjectByProjectIdentifier(projectIdentifier: String): ProjectDto

    fun deleteProject(projectIdentifier: String)

    fun updateProject(projectIdentifier: String, projectUpdateDto: ProjectUpdateDto)

    fun isUserOwnerOfProject(projectIdentifier: String, username: String)
}