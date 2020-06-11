package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.dto.AuthenticatedUserDto
import com.ducta.taskmanagement.dto.UserCredentials
import com.ducta.taskmanagement.dto.UserDto
import com.ducta.taskmanagement.dto.UserRegisterDto

interface UserService {
    fun getUserByUsername(username: String): UserDto

    fun registerUser(userRegisterDto: UserRegisterDto): AuthenticatedUserDto

    fun authenticateUser(userCredentials: UserCredentials): AuthenticatedUserDto
}