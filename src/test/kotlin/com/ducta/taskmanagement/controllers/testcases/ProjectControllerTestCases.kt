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
             "2020-10-02",
             "2020-11-02"
     )

     fun getTestCaseProjectInvalidDate(): ProjectCreateDto = ProjectCreateDto(
             "Project example",
             "DOC25",
             "demo case bad date",
             "2020-10-02",
             "2020-05-02"
     )

     fun getTestCaseAddNonExistedProject(): ProjectCreateDto = ProjectCreateDto(
             "non-existed project example",
             "KOTL2",
             "demo case non-existed project",
             "2020-10-02",
             "2020-11-02"
     )

     fun getTestCaseDeleteNonExistedProject(): String = "TEST1"

     fun getTestCaseDeleteExistedProject(): String = "KOTL2"

     fun getTestCaseUpdateExistedProject(): Map<String, ProjectUpdateDto> =
             mapOf("KOTL1" to ProjectUpdateDto(
                     "project KOTL1 updated name",
                     "new description",
                     "2020-12-12",
                     "2020-12-22"
             ))
 }

}