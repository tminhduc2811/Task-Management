package com.ducta.taskmanagement.exceptions

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

data class ErrorDetails(
        val time: Date,
        val status: Int,
        val message: String,
        val details: String,
        val path: String,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val errors: MutableMap<String, String?>? = null
)