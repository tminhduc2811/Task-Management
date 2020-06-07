package com.ducta.taskmanagement.controllers.testcases

import com.ducta.taskmanagement.dto.TaskCreateDto
import java.time.LocalDate

class BacklogControllerTestCases {

    companion object {
        fun getTestCaseCreateTaskSuccessfully(): Map<String, List<TaskCreateDto>> = mapOf("KOTL1" to listOf(
                TaskCreateDto(
                        "Task 1",
                        "Acceptance criteria",
                        "TODO",
                        "HIGH",
                        LocalDate.of(2020, 10, 10)
                ),
                TaskCreateDto(
                        "Task 2",
                        "Acceptance criteria",
                        "DOING",
                        "LOW",
                        LocalDate.of(2020, 8, 8)
                )
        ))

        // Expect project not found
        fun getTestCaseCreateTaskFailed(): Map<String, TaskCreateDto> = mapOf("BAD_ID" to
                TaskCreateDto(
                        "Task 1",
                        "Acceptance criteria",
                        "TODO",
                        "HIGH",
                        LocalDate.of(2020, 10, 10)
                )
        )

        // Expect bad request, invalid status
        fun getTestCaseInvalidEnumType(): Map<String, TaskCreateDto> = mapOf("KOTL1" to
                TaskCreateDto(
                        "Task",
                        "Acceptance criteria",
                        "BAD_STATUS",
                        "HIGH",
                        LocalDate.of(2020, 10, 10)
                )
        )

        fun getTestCaseUpdateTask(): Map<String, TaskCreateDto> = mapOf("KOTL1/KOTL1-1" to
                TaskCreateDto(
                        "Updated Task",
                        "Acceptance criteria",
                        "TODO",
                        "HIGH",
                        LocalDate.of(2020, 12, 12)
                )
        )

        fun getTestCaseUpdateFailed(): Map<String, TaskCreateDto> = mapOf("KOTL1/KOTL1-1" to
            TaskCreateDto(
                    "Updated Task",
                    "Acceptance criteria",
                    "BAD_STATUS",
                    "HIGH",
                    LocalDate.of(2020, 12, 12)
            )
        )
    }
}