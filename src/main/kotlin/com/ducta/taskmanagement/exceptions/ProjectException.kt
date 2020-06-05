package com.ducta.taskmanagement.exceptions

class ProjectAlreadyExistsException(val id: String?): Exception("Project with id: $id already exists")

class ProjectNotFoundException(val id: String?): Exception("Project with id: $id not found")

class ProjectInvalidDateException(): Exception("Project's end date must be after start date")