package com.ducta.taskmanagement.exceptions

import java.util.*

data class ErrorDetails (
        val time: Date,
        val status: Int,
        val message: String,
        val details: String,
        val path: String
)