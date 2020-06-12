package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.domain.dto.AuthenticatedUserDto
import com.ducta.taskmanagement.domain.dto.UserCredentials
import com.ducta.taskmanagement.domain.dto.UserDto
import com.ducta.taskmanagement.domain.dto.UserRegisterDto

interface UserService {
    fun getUserByUsername(username: String): UserDto

    fun registerUser(userRegisterDto: UserRegisterDto)

    fun authenticateUser(userCredentials: UserCredentials): AuthenticatedUserDto
}