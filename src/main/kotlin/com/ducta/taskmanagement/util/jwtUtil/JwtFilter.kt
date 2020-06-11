package com.ducta.taskmanagement.util.jwtUtil

import com.ducta.taskmanagement.security.UserAuthenticationService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(private val jwtTokenProvider: JwtTokenProvider,
                private val userAuthenticationService: UserAuthenticationService) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val jwt: String? = jwtTokenProvider.getJwtFromRequest(request)
            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                val username: String? = jwtTokenProvider.getUsernameFromJWT(jwt)
                val userDetails: UserDetails? = userAuthenticationService.loadUserByUsername(username)
                if (userDetails != null) {
                    val authentication = UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities
                    )
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (ex: Exception) {
            throw ex
        }
        filterChain.doFilter(request, response)
    }
}