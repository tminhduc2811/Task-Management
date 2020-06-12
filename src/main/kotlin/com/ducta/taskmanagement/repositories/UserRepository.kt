package com.ducta.taskmanagement.repositories

import com.ducta.taskmanagement.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {

    @Query("SELECT COUNT(u)>0 FROM User u WHERE u.username=:username")
    fun isUsernameExist(@Param("username") username: String): Boolean

    @Query("SELECT u FROM User u WHERE u.username=:username")
    fun findUserByUsername(@Param("username") username: String?): Optional<User>

    @Query("SELECT COUNT(u)>0 FROM User u WHERE u.email=:email")
    fun isEmailExist(@Param("email") email: String): Boolean

    @Query("SELECT COUNT(p)>0 FROM Project p WHERE p.user.username=:username AND p.projectIdentifier=:projectIdentifier")
    fun isUserOwnerOfProject(@Param("username") username: String, @Param("projectIdentifier") projectIdentifier: String): Boolean
}