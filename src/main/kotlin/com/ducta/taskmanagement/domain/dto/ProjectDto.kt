package com.ducta.taskmanagement.domain.dto

import com.ducta.taskmanagement.util.validator.DurationConstraint
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ProjectDto(
        val projectName: String,
        val projectIdentifier: String,
        val description: String,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val userId: Long
)

@DurationConstraint(
        startDate = "startDate",
        endDate = "endDate"
)
data class ProjectCreateDto(
        @get:NotBlank
        val projectName: String,
        @get:NotBlank
        @get:Size(min = 1, max = 5, message = "Project identifier must have 1 to 5 characters")
        val projectIdentifier: String,
        @get:NotBlank
        val description: String,
        val startDate: String,
        val endDate: String
)

@DurationConstraint(
        startDate = "startDate",
        endDate = "endDate"
)
data class ProjectUpdateDto(
        @get:NotBlank
        val projectName: String,
        @get:NotBlank
        val description: String,
        val startDate: String,
        val endDate: String
)