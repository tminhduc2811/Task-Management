package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.domain.dto.TaskCreateDto
import com.ducta.taskmanagement.domain.dto.TaskDto

interface BacklogService {
    fun getAllTasks(projectIdentifier: String): List<TaskDto>

    fun createTask(projectIdentifier: String, taskCreateDto: TaskCreateDto)

    fun deleteTask(projectIdentifier: String, taskSequence: String)

    fun updateTask(projectIdentifier: String, taskSequence: String, taskUpdateDto: TaskCreateDto)
}