package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.dto.ProjectCreateDto
import com.ducta.taskmanagement.dto.ProjectDto
import com.ducta.taskmanagement.exceptions.ProjectAlreadyExistsException
import com.ducta.taskmanagement.exceptions.ProjectNotFoundException
import com.ducta.taskmanagement.services.testcases.ProjectServiceTestCases
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = ["classpath:application-test.properties"])
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ProjectServiceTest {

    @Autowired
    private lateinit var projectService: ProjectService

    @Test
    @Order(1)
    fun testGetAllProjectsThenAddProject() {
        var projects: List<ProjectDto> = projectService.getAllProjectsByUsername(ProjectServiceTestCases.getTestUsername())
        Assertions.assertEquals(1, projects.size)
        // Add new project
        val newProject: ProjectCreateDto = ProjectServiceTestCases.getNewProjectTestCase()
        Assertions.assertDoesNotThrow {
            projectService.createProject(ProjectServiceTestCases.getTestUsername(), newProject)
        }

        // Get all projects again and make sure size equals to 2
        projects = projectService.getAllProjectsByUsername(ProjectServiceTestCases.getTestUsername())
        Assertions.assertEquals(2, projects.size)

        // Insert already existed project, this should throw exception

        val exception: ProjectAlreadyExistsException = Assertions.assertThrows(ProjectAlreadyExistsException::class.java) {
            projectService.createProject(ProjectServiceTestCases.getTestUsername(), newProject)
        }
        Assertions.assertEquals("Project with id: ${newProject.projectIdentifier} already exists", exception.message)
        println(exception.cause)
    }

    @Test
    @Order(2)
    fun testGetProjectByProjectIdentifier() {

        Assertions.assertDoesNotThrow {
            projectService.getProjectByProjectIdentifier("KOTL1")
        }
        val fakeProjectIdentifier = "fake1"
        val exception: ProjectNotFoundException = Assertions.assertThrows(ProjectNotFoundException::class.java) {
            projectService.getProjectByProjectIdentifier(fakeProjectIdentifier)
        }
        Assertions.assertEquals("Project with id: $fakeProjectIdentifier not found", exception.message)
    }

    @Test
    @Order(3)
    fun testUpdateProject() {
        val case = ProjectServiceTestCases.getUpdateProjectTestCase()
        val projectIdentifier = case.keys.first()
        val dto = case[projectIdentifier]

        val oldProject = projectService.getProjectByProjectIdentifier(projectIdentifier)

        Assertions.assertDoesNotThrow {
            projectService.updateProject(projectIdentifier, dto!!)
        }
        // Retrieve to check again
        val updatedProject = projectService.getProjectByProjectIdentifier(projectIdentifier)
        Assertions.assertEquals(dto!!.projectName, updatedProject.projectName)
        Assertions.assertEquals(dto.description, updatedProject.description)
        Assertions.assertEquals(dto.endDate, updatedProject.endDate)
        Assertions.assertEquals(dto.startDate, updatedProject.startDate)
        // Created date must be the same
        Assertions.assertEquals(oldProject.createdAt, updatedProject.createdAt)
        // Updated date must be different
        Assertions.assertNotEquals(oldProject.updatedAt, updatedProject.updatedAt)
    }

    @Test
    @Order(4)
    fun testDeleteProject() {
        val projectIdentifier = ProjectServiceTestCases.getDeleteProjectTestCase()
        // Delete the project
        Assertions.assertDoesNotThrow {
            projectService.deleteProject(projectIdentifier)
        }
        // Retrieve to check again
        val exception: ProjectNotFoundException =  Assertions.assertThrows(ProjectNotFoundException::class.java) {
            projectService.getProjectByProjectIdentifier(projectIdentifier)
        }
        Assertions.assertEquals("Project with id: $projectIdentifier not found", exception.message)
    }
}