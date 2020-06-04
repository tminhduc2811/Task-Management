package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.dto.UserDto
import com.ducta.taskmanagement.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/user/{id}")
    fun getUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<UserDto> {
        println("test $userId")
        return ResponseEntity.ok(userService.getUserById(userId))
    }
}