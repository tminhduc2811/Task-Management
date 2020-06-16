package com.ducta.taskmanagement.services.impl

import com.ducta.taskmanagement.domain.dto.TaskCreateDto
import com.ducta.taskmanagement.domain.dto.TaskDto
import com.ducta.taskmanagement.domain.Backlog
import com.ducta.taskmanagement.domain.Priority
import com.ducta.taskmanagement.domain.Status
import com.ducta.taskmanagement.domain.Task
import com.ducta.taskmanagement.exceptions.CustomException
import com.ducta.taskmanagement.repositories.BacklogRepository
import com.ducta.taskmanagement.repositories.TaskRepository
import com.ducta.taskmanagement.services.BacklogService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class BacklogServiceImpl(
        private val backlogRepository: BacklogRepository,
        private val taskRepository: TaskRepository) : BacklogService {

    override fun getAllTasks(projectIdentifier: String): List<TaskDto> {
        val backlog: Backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier)
                .map { it }
                .orElseThrow { throw CustomException("Project $projectIdentifier not found") }
        return backlog.tasks.map { task -> task.toDto() }.toList()
    }

    override fun createTask(projectIdentifier: String, taskCreateDto: TaskCreateDto) {
        val backlog: Backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier)
                .map { it }
                .orElseThrow { throw CustomException("Project $projectIdentifier not found") }

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
                .orElseThrow { throw CustomException("Project $projectIdentifier not found") }
        val task: Task = backlog.tasks.firstOrNull { it.sequence == taskSequence }
                ?: throw CustomException("Task $taskSequence not found")
        taskRepository.delete(task)
    }

    override fun updateTask(projectIdentifier: String, taskSequence: String, taskUpdateDto: TaskCreateDto) {
        val backlog: Backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier)
                .map { it }
                .orElseThrow { throw CustomException("Project $projectIdentifier not found") }
        val task: Task = backlog.tasks.firstOrNull { it.sequence == taskSequence }
                ?: throw CustomException("Task $taskSequence not found")
        // Update task fields
        val updatedTask = task.copy(
                summary = taskUpdateDto.summary,
                updatedAt = LocalDateTime.now(),
                priority = Priority.values()[taskUpdateDto.priority],
                status = Status.values()[taskUpdateDto.status],
                dueDate = LocalDate.parse(taskUpdateDto.dueDate),
                acceptanceCriteria = taskUpdateDto.acceptanceCriteria
        )
        updatedTask.backlog = task.backlog
        taskRepository.save(updatedTask)
    }
}