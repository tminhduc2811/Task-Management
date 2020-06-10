package com.ducta.taskmanagement.exceptions

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
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
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import java.util.function.Consumer
import javax.servlet.http.HttpServletRequest

@Order(Ordered.HIGHEST_PRECEDENCE)
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

    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ErrorDetails(
                Date(),
                404,
                "Not found",
                "No mapping for ${(request as ServletWebRequest).request.requestURI}",
                request.request.requestURI
        ), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        return ResponseEntity(ErrorDetails(
                Date(),
                500,
                "Internal server error",
                e.message.toString(),
                request.requestURI
        ), HttpStatus.INTERNAL_SERVER_ERROR)
    }


    @ExceptionHandler(ProjectAccessDeniedException::class)
    fun handleProjectAccessDeniedException(ex: ProjectAccessDeniedException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                Date(),
                400,
                "Bad request",
                ex.message!!,
                request.requestURI
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
    fun handleProjectNotFoundException(ex: ProjectNotFoundException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
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