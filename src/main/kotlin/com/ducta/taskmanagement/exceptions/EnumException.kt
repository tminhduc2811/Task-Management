package com.ducta.taskmanagement.exceptions

data class BadStatusException(val status: String): Exception("Status: $status is not valid")

data class BadPriorityException(val priority: String): Exception("Priority: $priority is not valid")