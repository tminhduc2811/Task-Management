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

//    @Override
//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    overfun handleValidationExceptions(ex: MethodArgumentNotValidException,
//    headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Map<String, String?>?> {
//        val errors: MutableMap<String, String?> = HashMap()
//        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
//            val fieldName = (error as FieldError).field
//            val errorMessage = error.getDefaultMessage()
//            errors[fieldName] = errorMessage
//        })
//        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
//    }

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

    @ExceptionHandler(value = [(ProjectAlreadyExistsException::class)])
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

    @ExceptionHandler(value = [(ProjectNotFoundException::class)])
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

    @ExceptionHandler(value = [(BadPriorityException::class)])
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

    @ExceptionHandler(value = [(BadStatusException::class)])
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

    @ExceptionHandler(value = [(TaskNotFoundException::class)])
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
}