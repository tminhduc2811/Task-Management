package com.ducta.taskmanagement.services.testcases

import com.ducta.taskmanagement.dto.ProjectDto
import com.ducta.taskmanagement.entities.Project
import com.ducta.taskmanagement.entities.User
import java.time.LocalDate
import java.time.LocalDateTime

class ProjectServiceTestCases  {
    companion object {
        fun getExistProject(): Project {
            val project = Project(
                    "KOTL1",
                    "Tranning Kotlin",
                    "description",
                    LocalDate.of(2020, 10, 10),
                    LocalDate.of(2020, 11, 11),
                    LocalDateTime.of(2020, 10, 10, 14, 20, 20),
                    LocalDateTime.of(2020, 10, 10, 14, 20, 20)
            )
            project.user = getUser()
            return project
        }
        fun getExistProject2(): Project {
            val project = Project(
                    "KOTL2",
                    "Tranning Kotlin",
                    "description",
                    LocalDate.of(2020, 10, 10),
                    LocalDate.of(2020, 11, 11),
                    LocalDateTime.of(2020, 10, 10, 14, 20, 20),
                    LocalDateTime.of(2020, 10, 10, 14, 20, 20)
            )
            project.user = getUser()
            return project
        }

        fun getListProjects() = listOf(getExistProject(), getExistProject2())

        fun getListProjectDto():List<ProjectDto> {
            return getListProjects().map { it.toDto() }
        }

        fun getUser() = User(
                1,
                "admin",
                "admin@gmail.com",
                "somePassword",
                "Ta Minh Duc",
                "",
                null
        )
        fun getNonExistUser() = "fakeUser"
        fun getNonExistProject() = Project(
                "KOTL2",
                "Non existed project",
                "description",
                LocalDate.of(2020, 10, 10),
                LocalDate.of(2020, 11, 11),
                LocalDateTime.now(),
                LocalDateTime.now()
        )
    }
}