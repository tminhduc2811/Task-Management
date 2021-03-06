package com.ducta.taskmanagement.domain.dto

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
        val status: Int,
        val priority: Int,
        val dueDate: LocalDate?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
)

data class TaskCreateDto(
        @get:NotBlank(message = "Summary is required")
        val summary: String,
        @get:NotBlank(message = "Acceptance criteria is required")
        val acceptanceCriteria: String,
        @EnumConstraint(field = "status")
        val status: Int,
        @EnumConstraint(field = "priority")
        val priority: Int,
        @DateConstraint
        val dueDate: String?
)
