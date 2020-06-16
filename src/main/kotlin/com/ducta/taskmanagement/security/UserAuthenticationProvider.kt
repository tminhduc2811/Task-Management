package com.ducta.taskmanagement.security

import com.ducta.taskmanagement.exceptions.CustomException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserAuthenticationProvider(
        private val userAuthenticationService: UserAuthenticationService,
        private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication? {
        try {
            val userAuthenticationDetails = userAuthenticationService.loadUserByUsername(authentication!!.name)
            if (
                    (userAuthenticationDetails != null) &&
                    (userAuthenticationDetails.username == authentication.name) &&
                    (passwordEncoder.matches(authentication.credentials.toString(), userAuthenticationDetails.password))) {
                return UsernamePasswordAuthenticationToken(userAuthenticationDetails.username, null, userAuthenticationDetails.authorities)
            }
            throw CustomException("Invalid password", HttpStatus.UNAUTHORIZED)
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication!! == UsernamePasswordAuthenticationToken::class
    }

}