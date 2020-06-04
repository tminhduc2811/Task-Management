package com.ducta.taskmanagement.exceptions

class ProjectAlreadyExistsException(val id: String?): Exception("Project with id: $id already exists")

class ProjectNotFoundException(val id: String?): Exception("Project with id: $id not found")