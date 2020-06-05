package com.ducta.taskmanagement.exceptions

class TaskNotFoundException(taskName: String): Exception("Task $taskName not found")
