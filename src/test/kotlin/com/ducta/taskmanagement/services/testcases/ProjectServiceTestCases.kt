package com.ducta.taskmanagement.services.testcases

import com.ducta.taskmanagement.dto.ProjectCreateDto
import java.time.LocalDate

class ProjectServiceTestCases {
    companion object {
        fun getNewProjectTestCase() = ProjectCreateDto(
                "New project",
                "KOTL5",
                "test project",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 2, 12),
                1
        )
    }
}