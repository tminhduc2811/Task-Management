package com.ducta.taskmanagement.services.impl

import com.ducta.taskmanagement.domain.dto.ProjectCreateDto
import com.ducta.taskmanagement.domain.dto.ProjectDto
import com.ducta.taskmanagement.domain.dto.ProjectUpdateDto
import com.ducta.taskmanagement.domain.Backlog
import com.ducta.taskmanagement.domain.Project
import com.ducta.taskmanagement.domain.TaskCount
import com.ducta.taskmanagement.domain.User
import com.ducta.taskmanagement.exceptions.*
import com.ducta.taskmanagement.repositories.ProjectRepository
import com.ducta.taskmanagement.services.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProjectServiceImpl(
        private val projectRepository: ProjectRepository) : ProjectService {

    override fun createProject(user: User, projectCreateDto: ProjectCreateDto) {

        projectRepository.findById(projectCreateDto.projectIdentifier)
                .ifPresent { throw CustomException("Project Identifier already existed") }
        val taskCount = TaskCount()
        val project: Project = Project.fromDto(projectCreateDto)
        val backlog = Backlog()
        backlog.taskCount = taskCount
        backlog.project = project
        taskCount.backlog = backlog

        project.user = user
        project.backlog = backlog
        projectRepository.save(project)

    }

    override fun getAllProjectsByUsername(username: String): List<ProjectDto> {
        return projectRepository.findAllProjectsByUsername(username)
                .map { project -> project.toDto() }.toList()
    }

    override fun getProjectByProjectIdentifier(projectIdentifier: String): ProjectDto {
        return projectRepository.findById(projectIdentifier).map {
            it.toDto()
        }.orElseThrow { throw CustomException("Project $projectIdentifier not found") }
    }

    override fun deleteProject(projectIdentifier: String) {

        projectRepository.findById(projectIdentifier)
                .map { p -> projectRepository.delete(p) }
                .orElseThrow { throw CustomException("Project $projectIdentifier not found") }

    }

    override fun updateProject(projectIdentifier: String, projectUpdateDto: ProjectUpdateDto) {
        projectRepository.findById(projectIdentifier)
                .map { project ->
                    val updatedProject: Project = project
                            .copy(
                                    projectName = projectUpdateDto.projectName,
                                    description = projectUpdateDto.description,
                                    startDate = Project.setDate(projectUpdateDto.startDate),
                                    endDate = Project.setDate(projectUpdateDto.endDate),
                                    updatedAt = LocalDateTime.now()
                            )
                    updatedProject.backlog = project.backlog
                    updatedProject.user = project.user
                    projectRepository.save(updatedProject)
                }.orElseThrow { throw CustomException("Project $projectIdentifier not found") }
    }

    override fun isUserOwnerOfProject(projectIdentifier: String, username: String) {
        projectRepository.findById(projectIdentifier).ifPresent {
            if (it.user!!.username != username) {
                throw CustomException("Access denied", HttpStatus.FORBIDDEN)
            }
        }

    }
}