package com.ducta.taskmanagement.security

import com.ducta.taskmanagement.domain.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserAuthenticationDetails(val user: User) : UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return user.projects!!.map { SimpleGrantedAuthority(it.projectIdentifier) }
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}