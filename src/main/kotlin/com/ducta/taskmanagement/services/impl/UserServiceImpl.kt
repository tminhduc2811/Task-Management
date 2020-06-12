package com.ducta.taskmanagement.services.impl

import com.ducta.taskmanagement.domain.dto.AuthenticatedUserDto
import com.ducta.taskmanagement.domain.dto.UserCredentials
import com.ducta.taskmanagement.domain.dto.UserDto
import com.ducta.taskmanagement.domain.dto.UserRegisterDto
import com.ducta.taskmanagement.domain.User
import com.ducta.taskmanagement.exceptions.*
import com.ducta.taskmanagement.repositories.UserRepository
import com.ducta.taskmanagement.services.UserService
import com.ducta.taskmanagement.util.jwtUtil.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder(),
                      private val authenticationManager: AuthenticationManager,
                      private val jwtTokenProvider: JwtTokenProvider) : UserService {
    override fun getUserByUsername(username: String): UserDto {
        return userRepository.findUserByUsername(username).map { user -> user.toDTO() }
                .orElseThrow { EntityNotFoundException("User not found") }
    }

    override fun registerUser(userRegisterDto: UserRegisterDto) {
        if (userRepository.isUsernameExist(userRegisterDto.username)) {
            throw EntityAlreadyExistedException("Username: ${userRegisterDto.username} already existed")
        }
        if (userRepository.isEmailExist(userRegisterDto.email)) {
            throw EntityAlreadyExistedException("Email: ${userRegisterDto.email} already existed")
        }
        val user = User(
                username = userRegisterDto.username,
                email = userRegisterDto.email,
                fullName = userRegisterDto.fullName,
                password = passwordEncoder.encode(userRegisterDto.password)
        )

        userRepository.save(user)
    }

    override fun authenticateUser(userCredentials: UserCredentials): AuthenticatedUserDto {
        try {
            val authentication: Authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            userCredentials.username,
                            userCredentials.password
                    )
            )
            return AuthenticatedUserDto(
                    userCredentials.username,
                    jwtTokenProvider.generateToken(authentication)
            )
        } catch (ex: Exception) {
            throw AuthenticationException()
        }
    }
}