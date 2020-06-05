package com.ducta.taskmanagement.security

import com.ducta.taskmanagement.exceptions.UserNotFoundException
import com.ducta.taskmanagement.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserAuthenticationService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        return userRepository.findUserByUsername(username)
                .map { UserAuthenticationDetails(it) }
                .orElseThrow { throw UserNotFoundException(username!!) }
    }
}