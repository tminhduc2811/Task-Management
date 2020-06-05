package com.ducta.taskmanagement.dto

import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


data class TaskDto (
        val id: Long,
        val backlogId: Long,
        val sequence: String,
        val summary: String,
        val acceptanceCriteria: String,
        val status: String,
        val priority: String,
        val dueDate: LocalDate,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
)

data class TaskCreateDto(
        @get:NotBlank(message = "Summary is required")
        val summary: String,
        @get:NotBlank(message = "Acceptance criteria is required")
        val acceptanceCriteria: String,
        val status: String,
        val priority: String,
        val dueDate: LocalDate
)
