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
import com.ducta.taskmanagement.repositories.UserRepository
import com.ducta.taskmanagement.services.ProjectService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ProjectServiceImpl(
        private val projectRepository: ProjectRepository,
        private val userRepository: UserRepository) : ProjectService {

    override fun createProject(username: String, projectCreateDto: ProjectCreateDto) {

        projectRepository.findById(projectCreateDto.projectIdentifier)
                .ifPresent { throw EntityAlreadyExistedException("Project Identifier already existed") }

        val user: User = userRepository.findUserByUsername(username).map { user -> user }
                .orElseThrow { throw EntityNotFoundException("User not found") }
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
        }.orElseThrow { throw EntityNotFoundException("Project $projectIdentifier not found") }
    }

    override fun deleteProject(projectIdentifier: String) {

        projectRepository.findById(projectIdentifier)
                .map { p -> projectRepository.delete(p) }
                .orElseThrow { throw EntityNotFoundException("Project $projectIdentifier not found") }

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
                }.orElseThrow { throw EntityNotFoundException("Project $projectIdentifier not found") }
    }

    override fun isUserOwnerOfProject(projectIdentifier: String, username: String) {
        projectRepository.findById(projectIdentifier).ifPresent {
            if (it.user!!.username != username) {
                throw EntityAccessDeniedException()
            }
        }

    }
}