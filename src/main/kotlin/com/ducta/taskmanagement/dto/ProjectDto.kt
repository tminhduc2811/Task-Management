package com.ducta.taskmanagement.dto

import com.ducta.taskmanagement.util.validator.NullableNotBlank
import java.time.LocalDate
import java.time.LocalDateTime

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
        @NullableNotBlank
        val projectName: String,
        val projectIdentifier: String,
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