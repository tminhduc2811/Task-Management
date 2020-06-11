package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.dto.AuthenticatedUserDto
import com.ducta.taskmanagement.dto.UserCredentials
import com.ducta.taskmanagement.dto.UserDto
import com.ducta.taskmanagement.dto.UserRegisterDto
import com.ducta.taskmanagement.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/users/{username}")
    fun getUserByUsername(@PathVariable(value = "username") username: String): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.getUserByUsername(username))
    }

    @PostMapping("/users/register")
    fun registerUser(@Valid @RequestBody userRegisterDto: UserRegisterDto): ResponseEntity<AuthenticatedUserDto> {
        return ResponseEntity(userService.registerUser(userRegisterDto), HttpStatus.CREATED)
    }

    @PostMapping("/users/login")
    fun authenticateUser(@RequestBody userCredentials: UserCredentials): ResponseEntity<AuthenticatedUserDto> {
        return ResponseEntity(userService.authenticateUser(userCredentials), HttpStatus.OK)
    }
}