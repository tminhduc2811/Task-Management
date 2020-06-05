package com.ducta.taskmanagement.exceptions

class BadStatusException(status: String): Exception("Status: $status is not valid")

class BadPriorityException(priority: String): Exception("Priority: $priority is not valid")