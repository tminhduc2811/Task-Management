package com.ducta.taskmanagement.controllers.testcases

import com.ducta.taskmanagement.dto.ProjectCreateDto
import com.ducta.taskmanagement.dto.ProjectUpdateDto
import com.ducta.taskmanagement.dto.UserCredentials
import java.time.LocalDate

class ProjectControllerTestCases {
 companion object {

     fun getTestCaseForLogin(): UserCredentials = UserCredentials(
             "admin",
             "Admin@1997"
     )

     fun getTestCaseAddExistedProject(): ProjectCreateDto = ProjectCreateDto(
             "existed project example",
             "KOTL1",
             "demo case existed project",
             LocalDate.of(2020, 10, 2),
             LocalDate.of(2021, 11, 2)
     )

     fun getTestCaseProjectInvalidDate(): ProjectCreateDto = ProjectCreateDto(
             "Project example",
             "DOC25",
             "demo case bad date",
             LocalDate.of(2020, 10, 2),
             LocalDate.of(2020, 5, 2)
     )

     fun getTestCaseAddNonExistedProject(): ProjectCreateDto = ProjectCreateDto(
             "non-existed project example",
             "KOTL2",
             "demo case non-existed project",
             LocalDate.of(2020, 10, 2),
             LocalDate.of(2021, 11, 2)
     )

     fun getTestCaseDeleteNonExistedProject(): String = "TEST1"

     fun getTestCaseDeleteExistedProject(): String = "KOTL2"

     fun getTestCaseUpdateExistedProject(): Map<String, ProjectUpdateDto> =
             mapOf("KOTL1" to ProjectUpdateDto(
                     "project KOTL1 updated name",
                     "new description",
                     LocalDate.of(2020, 12, 12),
                     LocalDate.of(2021, 1, 1)
             ))
 }

}