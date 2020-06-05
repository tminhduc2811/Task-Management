package com.ducta.taskmanagement.util.jwtUtil

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider {
    @Value("\${security.jwt.token.secret-key}")
    private val SECRET: String = ""

    @Value("\${security.jwt.token.expire-length}")
    private val EXPIRATION: Long = 3600000

    // Generate JWT Token base on user's information
    fun generateToken(userDetails: Authentication): String {
        val now = Date()
        val expiryDate = Date(now.time + EXPIRATION)
        return Jwts.builder()
                .setSubject(userDetails.principal.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()
    }

    fun getUsernameFromJWT(token: String?): String? {
        val claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .body
        return claims.subject
    }

    fun validateToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken)
            return true
        } catch (ex: MalformedJwtException) {
            println("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            println("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            println("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            println("JWT claims string is empty.")
        }
        return false
    }

    fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}