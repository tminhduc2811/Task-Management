package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.domain.dto.TaskCreateDto
import com.ducta.taskmanagement.domain.dto.TaskDto
import com.ducta.taskmanagement.services.BacklogService
import com.ducta.taskmanagement.services.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class BacklogController(private val backlogService: BacklogService, private val projectService: ProjectService) {
    @GetMapping("/backlog/{projectIdentifier}")
    fun getAllTasks(@PathVariable projectIdentifier: String): ResponseEntity<List<TaskDto>> {
        return ResponseEntity(backlogService.getAllTasks(projectIdentifier), HttpStatus.OK)
    }

    @PostMapping("/backlog/{projectIdentifier}")
    fun createTask(@PathVariable projectIdentifier: String,
                   @Valid @RequestBody taskCreateDto: TaskCreateDto): ResponseEntity<Void> {
        backlogService.createTask(projectIdentifier, taskCreateDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("/backlog/{projectIdentifier}/{taskSequence}")
    fun deleteTask(@PathVariable projectIdentifier: String,
                   @PathVariable taskSequence: String): ResponseEntity<Void> {
        backlogService.deleteTask(projectIdentifier, taskSequence)
        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("/backlog/{projectIdentifier}/{taskSequence}")
    fun updateTask(@PathVariable projectIdentifier: String,
                   @PathVariable taskSequence: String,
                   @Valid @RequestBody taskUpdateDto: TaskCreateDto): ResponseEntity<Void> {
        backlogService.updateTask(projectIdentifier, taskSequence, taskUpdateDto)
        return ResponseEntity(HttpStatus.OK)
    }
}