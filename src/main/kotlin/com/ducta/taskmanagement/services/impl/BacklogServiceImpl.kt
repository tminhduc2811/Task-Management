package com.ducta.taskmanagement.services.impl

import com.ducta.taskmanagement.dto.ProjectUpdateDto
import com.ducta.taskmanagement.dto.TaskCreateDto
import com.ducta.taskmanagement.dto.TaskDto
import com.ducta.taskmanagement.entities.Backlog
import com.ducta.taskmanagement.entities.Priority
import com.ducta.taskmanagement.entities.Status
import com.ducta.taskmanagement.entities.Task
import com.ducta.taskmanagement.exceptions.BadPriorityException
import com.ducta.taskmanagement.exceptions.BadStatusException
import com.ducta.taskmanagement.exceptions.ProjectNotFoundException
import com.ducta.taskmanagement.exceptions.TaskNotFoundException
import com.ducta.taskmanagement.repositories.BacklogRepository
import com.ducta.taskmanagement.repositories.ProjectRepository
import com.ducta.taskmanagement.repositories.TaskRepository
import com.ducta.taskmanagement.services.BacklogService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BacklogServiceImpl(
        private val backlogRepository: BacklogRepository,
        private val taskRepository: TaskRepository) : BacklogService {

    override fun getAllTasks(projectIdentifier: String): List<TaskDto> {
        val backlog: Backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier)
                .map { it }
                .orElseThrow { throw ProjectNotFoundException(projectIdentifier) }
        return backlog.tasks.map { task -> task.toDto() }.toList()
    }

    override fun createTask(projectIdentifier: String, taskCreateDto: TaskCreateDto) {
        val backlog: Backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier)
                .map { it }
                .orElseThrow { throw ProjectNotFoundException(projectIdentifier) }

        if (!Priority.values().any() { it.name == taskCreateDto.priority }) {
            throw BadPriorityException(taskCreateDto.priority)
        }
        if (!Status.values().any() { it.name == taskCreateDto.status }) {
            throw BadStatusException(taskCreateDto.status)
        }
        val task: Task = Task.fromDto(taskCreateDto)
        task.backlog = backlog
        backlog.taskCount.total += 1
        task.sequence = "$projectIdentifier-${backlog.taskCount.total}"
        backlog.addTask(task)
        backlogRepository.save(backlog)
    }

    override fun deleteTask(projectIdentifier: String, taskSequence: String) {
        val backlog: Backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier)
                .map { it }
                .orElseThrow { throw ProjectNotFoundException(projectIdentifier) }
        val task: Task = backlog.tasks.firstOrNull { it.sequence == taskSequence }
                ?: throw TaskNotFoundException(taskSequence)
        taskRepository.delete(task)
    }

    override fun updateTask(projectIdentifier: String, taskSequence: String, taskUpdateDto: TaskCreateDto) {
        val backlog: Backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier)
                .map { it }
                .orElseThrow { throw ProjectNotFoundException(projectIdentifier) }
        val task: Task = backlog.tasks.firstOrNull { it.sequence == taskSequence }
                ?: throw TaskNotFoundException(taskSequence)
        // Update task fields
        val updatedTask = task.copy(
                summary = taskUpdateDto.summary,
                updatedAt = LocalDateTime.now(),
                priority = Priority.valueOf(taskUpdateDto.priority),
                status = Status.valueOf(taskUpdateDto.status),
                dueDate = taskUpdateDto.dueDate,
                acceptanceCriteria = taskUpdateDto.acceptanceCriteria
        )
        taskRepository.save(updatedTask)
    }
}