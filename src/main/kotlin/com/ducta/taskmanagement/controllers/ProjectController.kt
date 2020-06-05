package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.dto.ProjectCreateDto
import com.ducta.taskmanagement.dto.ProjectDto
import com.ducta.taskmanagement.dto.ProjectUpdateDto
import com.ducta.taskmanagement.services.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ProjectController(private val projectService: ProjectService) {

    @PostMapping("/projects")
    fun createProject(@Valid @RequestBody projectCreateDto: ProjectCreateDto) : ResponseEntity<Void> {
        projectService.createProject(projectCreateDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/projects")
    fun getAllProjects(): ResponseEntity<List<ProjectDto>> {
        // TODO: Change Id by getting user info
        return ResponseEntity(projectService.getAllProjectsByUserId(1L), HttpStatus.OK)
    }

    @GetMapping("/projects/{projectIdentifier}")
    fun getProjectByProjectIdentifier(@PathVariable projectIdentifier: String): ResponseEntity<ProjectDto> {
        return ResponseEntity(projectService.getProjectByProjectIdentifier(projectIdentifier), HttpStatus.OK)
    }

    @DeleteMapping("/projects/{projectIdentifier}")
    fun deleteProject(@PathVariable projectIdentifier: String): ResponseEntity<Void> {
        projectService.deleteProject(projectIdentifier)
        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("/projects/{projectIdentifier}")
    fun updateProject(@PathVariable projectIdentifier: String,
                      @RequestBody projectUpdateDto: ProjectUpdateDto): ResponseEntity<Void> {
        projectService.updateProject(projectIdentifier, projectUpdateDto)
        return ResponseEntity(HttpStatus.OK)
    }
}