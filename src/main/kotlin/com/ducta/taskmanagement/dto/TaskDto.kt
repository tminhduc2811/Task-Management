package com.ducta.taskmanagement.dto

import com.ducta.taskmanagement.util.validator.DateConstraint
import com.ducta.taskmanagement.util.validator.EnumConstraint
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

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
        @get:NotBlank
        @EnumConstraint(field = "status")
        val status: String,
        @get:NotBlank
        @EnumConstraint(field = "priority")
        val priority: String,
        @DateConstraint
        val dueDate: String
)
