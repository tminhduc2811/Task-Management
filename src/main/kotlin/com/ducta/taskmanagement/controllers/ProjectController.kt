package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.domain.dto.ProjectCreateDto
import com.ducta.taskmanagement.domain.dto.ProjectDto
import com.ducta.taskmanagement.domain.dto.ProjectUpdateDto
import com.ducta.taskmanagement.security.UserAuthenticationDetails
import com.ducta.taskmanagement.services.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ProjectController(private val projectService: ProjectService) {

    @PostMapping("/projects")
    fun createProject(@Valid @RequestBody projectCreateDto: ProjectCreateDto) : ResponseEntity<Void> {
        val user = (SecurityContextHolder.getContext().authentication.principal as UserAuthenticationDetails).user
        projectService.createProject(user, projectCreateDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/projects")
    fun getAllProjects(): ResponseEntity<List<ProjectDto>> {
        val username = SecurityContextHolder.getContext().authentication.name
        return ResponseEntity(projectService.getAllProjectsByUsername(username), HttpStatus.OK)
    }

    @GetMapping("/projects/{projectIdentifier}")
    fun getProjectByProjectIdentifier(@PathVariable("projectIdentifier") projectIdentifier: String): ResponseEntity<ProjectDto> {
        return ResponseEntity(projectService.getProjectByProjectIdentifier(projectIdentifier), HttpStatus.OK)
    }

    @DeleteMapping("/projects/{projectIdentifier}")
    fun deleteProject(@PathVariable("projectIdentifier") projectIdentifier: String): ResponseEntity<Void> {
        projectService.deleteProject(projectIdentifier)
        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("/projects/{projectIdentifier}")
    fun updateProject(@PathVariable projectIdentifier: String,
                      @RequestBody @Valid projectUpdateDto: ProjectUpdateDto): ResponseEntity<Void> {
        projectService.updateProject(projectIdentifier, projectUpdateDto)
        return ResponseEntity(HttpStatus.OK)
    }
}