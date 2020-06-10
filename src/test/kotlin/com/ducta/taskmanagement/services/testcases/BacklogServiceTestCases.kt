package com.ducta.taskmanagement.services.testcases

import com.ducta.taskmanagement.dto.TaskCreateDto
import java.time.LocalDate

class BacklogServiceTestCases {
    companion object {
        fun getGetAllTasksWithBadProjectIdTestCase() = "FAKE1"

        fun getAllTasksTestCase() = "KOTL1"

        fun getCreateTaskTestCase() = mapOf("KOTL1" to  TaskCreateDto(
                "new task",
                "test field",
                "TODO",
                "LOW",
                LocalDate.of(2020, 12, 12)
        ))
    }
}