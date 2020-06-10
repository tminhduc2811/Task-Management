package com.ducta.taskmanagement.security

import com.ducta.taskmanagement.exceptions.ProjectAccessDeniedException
import com.ducta.taskmanagement.repositories.ProjectRepository
import com.ducta.taskmanagement.repositories.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class SecurityUtil(private val userRepository: UserRepository,
                   private val projectRepository: ProjectRepository) {
    fun isOwner(projectIdentifier: String, authentication: Authentication): Boolean {
        println(authentication.name)

        if(!projectRepository.existsById(projectIdentifier)) {
            return true
        }
        if (authentication.isAuthenticated) {
            return userRepository.isUserOwnerOfProject(authentication.name, projectIdentifier)
        }
        return false
    }
}