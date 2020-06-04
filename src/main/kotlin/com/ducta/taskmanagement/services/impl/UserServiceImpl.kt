package com.ducta.taskmanagement.services.impl

import com.ducta.taskmanagement.dto.UserDto
import com.ducta.taskmanagement.repositories.UserRepository
import com.ducta.taskmanagement.services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun getUserById(userId: Long): UserDto {
        try {
            return userRepository.findById(userId).map { user -> user.toDTO() }.orElseThrow { Exception("Not found") }
        } catch (ex: Exception) {
            throw ex
        }
    }
}