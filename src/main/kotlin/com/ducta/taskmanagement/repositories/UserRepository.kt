package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
}