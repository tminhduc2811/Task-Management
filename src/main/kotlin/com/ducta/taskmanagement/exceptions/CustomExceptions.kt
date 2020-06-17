package com.ducta.taskmanagement.exceptions

import org.springframework.http.HttpStatus

class CustomException(override val message: String, val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST) : RuntimeException() {
}
