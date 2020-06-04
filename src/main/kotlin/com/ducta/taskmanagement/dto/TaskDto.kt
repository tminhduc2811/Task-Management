package com.ducta.taskmanagement.dto

import java.time.LocalDate
import java.time.LocalDateTime

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