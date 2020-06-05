package com.ducta.taskmanagement.exceptions

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import java.util.function.Consumer
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class CustomGlobalExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errors: MutableMap<String, String?>? = hashMapOf()
        ex.bindingResult.allErrors.forEach(
                Consumer { error: ObjectError ->
                    val fieldName = (error as FieldError).field
                    val errorMessage = error.defaultMessage
                    errors?.set(fieldName, errorMessage)
                })
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                "Validation failed",
                (request as ServletWebRequest).request.requestURI,
                errors
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ProjectAlreadyExistsException::class)
    fun handleProjectAlreadyExists(ex: ProjectAlreadyExistsException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ProjectNotFoundException::class)
    fun handleProjectNotFound(ex: ProjectNotFoundException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ProjectInvalidDateException::class)
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

    @ExceptionHandler(BadPriorityException::class)
    fun handleBadPriorityException(ex: BadPriorityException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BadStatusException::class)
    fun handleBadStatusException(ex: BadStatusException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TaskNotFoundException::class)
    fun handleTaskNotFoundException(ex: TaskNotFoundException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserNotFoundException(ex: UserAlreadyExistsException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailAlreadyExistsException(ex: EmailAlreadyExistsException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
        )
        return ResponseEntity(errorDetails, HttpStatus.UNAUTHORIZED)
    }
}