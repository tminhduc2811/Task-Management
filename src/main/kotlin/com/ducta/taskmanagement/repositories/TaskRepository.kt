package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.entities.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<Task, Long> {
}