package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.dto.ProjectCreateDto
import com.ducta.taskmanagement.dto.ProjectDto
import com.ducta.taskmanagement.dto.ProjectUpdateDto

interface ProjectService {
    fun createProject(projectCreateDto: ProjectCreateDto)

    fun getAllProjectsByUserId(id: Long): List<ProjectDto>

    fun getProjectByProjectIdentifier(projectIdentifier: String): ProjectDto

    fun deleteProject(projectIdentifier: String)

    fun updateProject(projectIdentifier: String, projectUpdateDto: ProjectUpdateDto)
}