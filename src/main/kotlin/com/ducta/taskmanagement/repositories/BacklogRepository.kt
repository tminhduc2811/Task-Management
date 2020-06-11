package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.entities.Backlog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BacklogRepository: JpaRepository<Backlog, Long> {

    @Query("SELECT b FROM Backlog b WHERE b.project.projectIdentifier=:projectIdentifier")
    fun findBacklogByProjectIdentifier(@Param("projectIdentifier") projectIdentifier: String): Optional<Backlog>
}