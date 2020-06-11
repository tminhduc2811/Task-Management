package com.ducta.taskmanagement.services.impl

import com.ducta.taskmanagement.dto.ProjectCreateDto
import com.ducta.taskmanagement.dto.ProjectDto
import com.ducta.taskmanagement.dto.ProjectUpdateDto
import com.ducta.taskmanagement.entities.Backlog
import com.ducta.taskmanagement.entities.Project
import com.ducta.taskmanagement.entities.TaskCount
import com.ducta.taskmanagement.entities.User
import com.ducta.taskmanagement.exceptions.ProjectAccessDeniedException
import com.ducta.taskmanagement.exceptions.ProjectAlreadyExistsException
import com.ducta.taskmanagement.exceptions.ProjectInvalidDateException
import com.ducta.taskmanagement.exceptions.ProjectNotFoundException
import com.ducta.taskmanagement.repositories.ProjectRepository
import com.ducta.taskmanagement.repositories.UserRepository
import com.ducta.taskmanagement.services.ProjectService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProjectServiceImpl(
        private val projectRepository: ProjectRepository,
        private val userRepository: UserRepository) : ProjectService {

    override fun createProject(username: String, projectCreateDto: ProjectCreateDto) {

        projectRepository.findById(projectCreateDto.projectIdentifier)
                .ifPresent { throw ProjectAlreadyExistsException(projectCreateDto.projectIdentifier) }
//        if (projectCreateDto.startDate.isAfter(projectCreateDto.endDate)) {
//            throw ProjectInvalidDateException()
//        }

        val user: User = userRepository.findUserByUsername(username).map { user -> user }
                .orElseThrow { throw Exception("User not found") }
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
            println(it)
            it.toDto()
        }.orElseThrow { throw ProjectNotFoundException(projectIdentifier) }
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
                    updatedProject.backlog = project.backlog
                    updatedProject.user = project.user
                    projectRepository.save(updatedProject)
                }.orElseThrow { throw ProjectNotFoundException(projectIdentifier) }
    }

    override fun isUserOwnerOfProject(projectIdentifier: String, username: String) {
        projectRepository.findById(projectIdentifier).ifPresent {
            if (it.user!!.username != username) {
                println(username)
                println(it.user!!.username)
                throw ProjectAccessDeniedException(projectIdentifier)
            }
        }

    }
}