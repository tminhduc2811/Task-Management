package com.ducta.taskmanagement.controllers.testcases

import com.ducta.taskmanagement.domain.dto.TaskCreateDto

class BacklogControllerTestCases {

    companion object {
        fun getTestCaseCreateTaskSuccessfully(): Map<String, List<TaskCreateDto>> = mapOf("KOTL1" to listOf(
                TaskCreateDto(
                        "Task 1",
                        "Acceptance criteria",
                        1,
                        2,
                        "2020-10-10"
                ),
                TaskCreateDto(
                        "Task 2",
                        "Acceptance criteria",
                        2,
                        1,
                        "2020-08-08"
                )
        ))

        // Expect project not found
        fun getTestCaseCreateTaskFailed(): Map<String, TaskCreateDto> = mapOf("BAD_ID" to
                TaskCreateDto(
                        "Task 1",
                        "Acceptance criteria",
                        1,
                        2,
                        "2020-10-10"
                )
        )

        // Expect bad request, invalid status
        fun getTestCaseInvalidEnumType(): Map<String, TaskCreateDto> = mapOf("KOTL1" to
                TaskCreateDto(
                        "Task",
                        "Acceptance criteria",
                        4,
                        1,
                        "2020-10-10"
                )
        )

        fun getTestCaseUpdateTask(): Map<String, TaskCreateDto> = mapOf("KOTL1/KOTL1-1" to
                TaskCreateDto(
                        "Updated Task",
                        "Acceptance criteria",
                        1,
                        2,
                        "2020-12-10"
                )
        )

        fun getTestCaseUpdateFailed(): Map<String, TaskCreateDto> = mapOf("KOTL1/KOTL1-1" to
            TaskCreateDto(
                    "Updated Task",
                    "Acceptance criteria",
                    5,
                    1,
                    "2020-10-10"
            )
        )
    }
}