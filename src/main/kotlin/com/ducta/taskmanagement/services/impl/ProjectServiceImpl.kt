package com.ducta.taskmanagement.services.impl

import com.ducta.taskmanagement.dto.ProjectCreateDto
import com.ducta.taskmanagement.dto.ProjectDto
import com.ducta.taskmanagement.dto.ProjectUpdateDto
import com.ducta.taskmanagement.entities.Backlog
import com.ducta.taskmanagement.entities.Project
import com.ducta.taskmanagement.entities.TaskCount
import com.ducta.taskmanagement.entities.User
import com.ducta.taskmanagement.exceptions.ProjectAlreadyExistsException
import com.ducta.taskmanagement.exceptions.ProjectInvalidDateException
import com.ducta.taskmanagement.exceptions.ProjectNotFoundException
import com.ducta.taskmanagement.repositories.ProjectRepository
import com.ducta.taskmanagement.repositories.UserRepository
import com.ducta.taskmanagement.services.ProjectService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProjectServiceImpl(
        private val projectRepository: ProjectRepository,
        private val userRepository: UserRepository) : ProjectService {

    override fun createProject(projectCreateDto: ProjectCreateDto) {
        try {
            projectRepository.findById(projectCreateDto.projectIdentifier)
                    .ifPresent { throw ProjectAlreadyExistsException(projectCreateDto.projectIdentifier) }
            if (projectCreateDto.startDate.isAfter(projectCreateDto.endDate)) {
                throw ProjectInvalidDateException()
            }

            val user: User = userRepository.findById(projectCreateDto.userId).map { user -> user }
                    .orElseThrow { throw Exception("User not found") }
            val taskCount = TaskCount()
            val project: Project = Project.fromDto(projectCreateDto)
            val backlog = Backlog(
                    taskCount = taskCount,
                    project = project
            )
            taskCount.backlog = backlog

            project.user = user
            project.backlog = backlog
            projectRepository.save(project)
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun getAllProjectsByUserId(id: Long): List<ProjectDto> {
        return projectRepository.findAllProjectsByUserId(id)
                .map { project -> project.toDto() }.toList()
    }

    override fun getProjectByProjectIdentifier(projectIdentifier: String): ProjectDto {
        return projectRepository.findById(projectIdentifier).map { project -> project.toDto() }
                .orElseThrow { throw ProjectNotFoundException(projectIdentifier) }
    }

    override fun deleteProject(projectIdentifier: String) {

        projectRepository.findById(projectIdentifier)
                .map { p -> projectRepository.delete(p) }
                .orElseThrow { throw ProjectNotFoundException(projectIdentifier) }

    }

    override fun updateProject(projectIdentifier: String, projectUpdateDto: ProjectUpdateDto) {
        projectRepository.findById(projectIdentifier)
                .map { project ->
                    if (projectUpdateDto.startDate.isAfter(projectUpdateDto.endDate)) {
                        throw ProjectInvalidDateException()
                    }
                    val updatedProject: Project = project
                            .copy(
                                    projectName = projectUpdateDto.projectName,
                                    description = projectUpdateDto.description,
                                    startDate = projectUpdateDto.startDate,
                                    endDate = projectUpdateDto.endDate,
                                    updatedAt = LocalDateTime.now()
                            )
                    projectRepository.save(updatedProject)
                }.orElseThrow { throw ProjectNotFoundException(projectIdentifier) }
    }
}