package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.entities.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: JpaRepository<Project, String> {

    @Query("SELECT p FROM Project p WHERE p.user.id=:userId")
    fun findAllProjectsByUserId(@Param("userId") userId: Long): List<Project>
}