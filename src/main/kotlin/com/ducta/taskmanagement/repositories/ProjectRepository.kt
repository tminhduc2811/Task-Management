package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.entities.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: JpaRepository<Project, String> {

    @Query("SELECT p FROM Project p WHERE p.user.username=:username")
    fun findAllProjectsByUsername(@Param("username") username: String): List<Project>

    @Query("SELECT COUNT(p)>0 FROM Project p WHERE p.projectIdentifier=:projectIdentifier")
    fun isProjectExist(@Param("projectIdentifier") projectIdentifier: String): Boolean

    @Query("SELECT COUNT(p)>0 FROM Project p WHERE p.user.username=:username AND p.projectIdentifier=:projectIdentifier")
    fun isUserOwnerOfProject(@Param("username") username: String, @Param("projectIdentifier") projectIdentifier: String): Boolean
}