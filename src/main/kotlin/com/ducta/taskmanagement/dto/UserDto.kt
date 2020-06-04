package com.ducta.taskmanagement.dto

data class UserDto(
        val id: Long,
        val username: String,
        val email: String,
        val fullName: String,
        val avatar: String?
)