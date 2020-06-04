package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.entities.Backlog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BacklogRepository: JpaRepository<Backlog, Long> {
}