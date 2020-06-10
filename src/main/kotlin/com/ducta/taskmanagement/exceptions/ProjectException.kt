package com.ducta.taskmanagement.exceptions

class ProjectAlreadyExistsException(id: String?): Exception("Project with id: $id already exists")

class ProjectNotFoundException(id: String?): Exception("Project with id: $id not found")

class ProjectInvalidDateException(): Exception("Project's end date must be after start date")

class ProjectAccessDeniedException(id: String): Exception("You don't have permission to access this project: $id")