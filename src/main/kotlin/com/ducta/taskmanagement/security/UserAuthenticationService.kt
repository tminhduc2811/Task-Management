package com.ducta.taskmanagement.security

import com.ducta.taskmanagement.exceptions.EntityNotFoundException
import com.ducta.taskmanagement.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserAuthenticationService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        return userRepository.findUserByUsername(username)
                .map { UserAuthenticationDetails(it) }
                .orElseThrow { throw EntityNotFoundException("User not found exception") }
    }
}