package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.entities.User

interface UserService {
    fun getUserById(userId: Long): User
}