package com.ducta.taskmanagement.dto

import com.ducta.taskmanagement.util.validator.DateConstraint
import com.ducta.taskmanagement.util.validator.DurationConstraint
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.math.min

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
        @DateConstraint
        val startDate: String,
        @DateConstraint
        val endDate: String
)

data class ProjectUpdateDto(
        val projectName: String,
        val description: String,
        @DateConstraint
        val startDate: LocalDate,
        @DateConstraint
        val endDate: LocalDate
)