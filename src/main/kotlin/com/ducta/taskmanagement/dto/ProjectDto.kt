package com.ducta.taskmanagement.dto

import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

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

data class ProjectCreateDto(
        @get:NotBlank
        val projectName: String,
        @get:NotBlank
        val projectIdentifier: String,
        @get:NotBlank
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val userId: Long
)

data class ProjectUpdateDto(
        val projectName: String,
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate
)