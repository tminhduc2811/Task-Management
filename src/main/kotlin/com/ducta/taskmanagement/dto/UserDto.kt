package com.ducta.taskmanagement.dto

import com.ducta.taskmanagement.util.validator.EmailConstraint
import com.ducta.taskmanagement.util.validator.PasswordConstraint
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserDto(
        val id: Long,
        val username: String,
        val email: String,
        val fullName: String,
        val avatar: String?
)

data class UserRegisterDto(
        @get:NotBlank
        @get:Size(min = 4, max = 20)
        val username: String,
        @EmailConstraint
        val email: String,
        @PasswordConstraint
        val password: String,
        @get:NotBlank
        val fullName: String
)

data class UserCredentials(
        val username: String,
        val password: String
)

data class AuthenticatedUserDto(
        val username: String,
        val token: String
)