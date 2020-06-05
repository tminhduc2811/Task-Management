package com.ducta.taskmanagement.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class CustomGlobalExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(ProjectAlreadyExistsException::class)])
    fun handleProjectAlreadyExists(ex: ProjectAlreadyExistsException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Cannot create new project",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(ProjectNotFoundException::class)])
    fun handleProjectNotFound(ex: ProjectNotFoundException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Cannot modify or delete project",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(ProjectInvalidDateException::class)])
    fun handleProjectInvalidDate(ex: ProjectInvalidDateException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Start date and End date are not suitable",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}