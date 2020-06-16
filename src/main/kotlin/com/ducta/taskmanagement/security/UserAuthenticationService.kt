package com.ducta.taskmanagement.security

import com.ducta.taskmanagement.exceptions.CustomException
import com.ducta.taskmanagement.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserAuthenticationService(private val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        return userRepository.getAllUserInfo(username)
                .map { UserAuthenticationDetails(it) }
                .orElseThrow { throw CustomException("Username found exception", HttpStatus.UNAUTHORIZED) }
    }
}