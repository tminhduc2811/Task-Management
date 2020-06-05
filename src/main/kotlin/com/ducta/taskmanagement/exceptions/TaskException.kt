package com.ducta.taskmanagement.exceptions

data class TaskNotFoundException(val taskName: String): Exception("Task $taskName not found")