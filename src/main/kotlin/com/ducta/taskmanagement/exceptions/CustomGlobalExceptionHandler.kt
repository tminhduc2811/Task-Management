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
                    // Field errors
                    if (error is FieldError) {
                        val fieldName = error.field
                        val errorMessage = error.defaultMessage
                        errors?.set(fieldName, errorMessage)
                    } else {
                        // Object error
                        for (i in error.arguments!!.indices) {
                            if (i != 0) {
                                errors?.set(error.arguments!![i].toString(), error.defaultMessage)
                            }
                        }
                    }
                }
        )
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

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException, request: HttpServletRequest): ResponseEntity<ErrorDetails> {
        return ResponseEntity(ErrorDetails(
                Date(),
                ex.httpStatus.value(),
                ex.httpStatus.name,
                ex.message,
                request.requestURI
        ), ex.httpStatus)
    }

}