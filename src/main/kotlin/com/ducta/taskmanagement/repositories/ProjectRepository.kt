package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.entities.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: JpaRepository<Project, Long> {
}