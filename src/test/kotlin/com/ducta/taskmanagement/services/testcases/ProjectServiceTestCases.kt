package com.ducta.taskmanagement.services.testcases

import com.ducta.taskmanagement.dto.ProjectCreateDto
import com.ducta.taskmanagement.dto.ProjectUpdateDto
import java.time.LocalDate

class ProjectServiceTestCases {
    companion object {
        fun getTestUsername() = "admin"
        fun getNewProjectTestCase() = ProjectCreateDto(
                "New project",
                "KOTL5",
                "test project",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 2, 12)
        )

        fun getUpdateProjectTestCase() = mapOf("KOTL5" to ProjectUpdateDto(
                "updated name",
                "updated description",
                LocalDate.of(2020, 2, 12),
                LocalDate.of(2020, 5, 11)
        ))

        fun getDeleteProjectTestCase() = "KOTL5"
    }
}