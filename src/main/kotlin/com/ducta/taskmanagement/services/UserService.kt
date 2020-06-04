package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.dto.UserDto

interface UserService {
    fun getUserById(userId: Long): UserDto
}