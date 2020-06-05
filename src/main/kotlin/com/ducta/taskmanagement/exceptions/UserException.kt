package com.ducta.taskmanagement.exceptions

class UserAlreadyExistsException(username: String): Exception("Username: $username already exists")
class EmailAlreadyExistsException(email: String): Exception("Email: $email already exists")
class UserNotFoundException(username: String): Exception("Username: $username not found")