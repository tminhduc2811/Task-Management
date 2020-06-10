package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.dto.TaskDto
import com.ducta.taskmanagement.exceptions.ProjectNotFoundException
import com.ducta.taskmanagement.services.testcases.BacklogServiceTestCases
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
class BacklogServiceTest {

    @Autowired
    private lateinit var backlogService: BacklogService

    @Test
    @Order(1)
    fun testAddTask() {

        val case = BacklogServiceTestCases.getCreateTaskTestCase()
        val projectIdentifier = case.keys.first()
        val projectCreateDto = case[projectIdentifier]

        // Retrieve all tasks before adding new task, this should return an empty array
        var tasks = backlogService.getAllTasks(projectIdentifier)
        Assertions.assertEquals(0, tasks.size)

        // Add new project
        Assertions.assertDoesNotThrow {
            backlogService.createTask(projectIdentifier, projectCreateDto!!)
        }

        // Retrieve to check again
        tasks = backlogService.getAllTasks(projectIdentifier)
        Assertions.assertEquals(1, tasks.size)

        // Assert that sequence follow the project identifier
        Assertions.assertEquals("$projectIdentifier-1", tasks[0].sequence)

    }

    @Test
    @Order(2)
    fun testGetAllTasks() {
        // Case 1, project identifier doesn not exist
        var projectIdentifier = BacklogServiceTestCases.getGetAllTasksWithBadProjectIdTestCase()
        val exception: ProjectNotFoundException = Assertions.assertThrows(ProjectNotFoundException::class.java) {
            backlogService.getAllTasks(projectIdentifier)
        }
        Assertions.assertEquals("Project with id: $projectIdentifier not found", exception.message)

        // Case 2, tasks size = 1
        projectIdentifier = BacklogServiceTestCases.getAllTasksTestCase()
        val tasks: List<TaskDto> = backlogService.getAllTasks(projectIdentifier)
        Assertions.assertEquals(1, tasks.size)

    }

    @Test
    @Order(3)
    fun testUpdateTask() {

    }
}