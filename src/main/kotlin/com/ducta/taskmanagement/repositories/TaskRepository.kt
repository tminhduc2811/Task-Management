package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.entities.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<Task, Long> {
//    @Query("SELECT t from Task t JOIN Backlog b ON t.backlog.id = b.id WHERE b.projectIdentifier=:projectIdentifier")
//    fun findAllTasksByProjectIdentifier(@Param("projectIdentifier") projectIdentifier: String): List<Task>
}