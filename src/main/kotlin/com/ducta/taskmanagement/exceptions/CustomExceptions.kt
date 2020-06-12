package com.ducta.taskmanagement.exceptions

class EntityNotFoundException(message: String): Exception(message)

class EntityAlreadyExistedException(message: String): Exception(message)

class InvalidStartDateEndDateException(): Exception("Start date must be before End date")

class EntityAccessDeniedException(): Exception("Access denied")

class AuthenticationException: Exception("Invalid username or password")