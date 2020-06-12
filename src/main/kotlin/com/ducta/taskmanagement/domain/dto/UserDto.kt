package com.ducta.taskmanagement.domain.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
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
        @get:Email(message = "Invalid email")
        val email: String,
        @get:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,40}\$", message = "Your password is not strong enough")
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