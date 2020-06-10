package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.controllers.testcases.BacklogControllerTestCases
import com.ducta.taskmanagement.controllers.testcases.ProjectControllerTestCases
import com.ducta.taskmanagement.dto.AuthenticatedUserDto
import com.ducta.taskmanagement.dto.TaskDto
import com.ducta.taskmanagement.exceptions.ErrorDetails
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = ["classpath:application-test.properties"])
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class BacklogControllerTest {


    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val url = "/api/backlog"

    companion object {
        private var headers = HttpHeaders()
    }

    @Test
    @Order(1)
    fun testLogin() {
        val userCredentials = ProjectControllerTestCases.getTestCaseForLogin()
        val responseEntity: ResponseEntity<AuthenticatedUserDto> = restTemplate.postForEntity(
                "/api/users/login",
                HttpEntity(userCredentials))
        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        headers.add("Authorization","Bearer ${responseEntity.body!!.token}")
    }

    @Test
    @Order(2)
    fun testCreateTask() {
        val createTaskCase1 = BacklogControllerTestCases.getTestCaseCreateTaskSuccessfully()
        val projectIdentifier = createTaskCase1.keys.first()
        for (task in createTaskCase1[projectIdentifier] ?: error("Project identifier of test not found")) {
            val entity = HttpEntity(task, headers)
            println(entity)
            val responseEntity: ResponseEntity<Any> = restTemplate.postForEntity("$url/$projectIdentifier", entity)
            println(responseEntity.body)
            Assertions.assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        }
        // Get all tasks
        val response: ResponseEntity<List<TaskDto>> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.GET, HttpEntity(null, headers))
        println(response.body)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(2, response.body!!.size)
        Assertions.assertEquals("KOTL1-1", response.body!![0].sequence)
        Assertions.assertEquals("KOTL1-2", response.body!![1].sequence)
    }

    @Test
    @Order(3)
    fun testCreateTaskWithNonExistedProjectIdentifier() {
        val createTaskCase = BacklogControllerTestCases.getTestCaseCreateTaskFailed()
        val projectIdentifier = createTaskCase.keys.first()
        val entity = HttpEntity(createTaskCase[projectIdentifier]!!, headers)
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.postForEntity("$url/$projectIdentifier", entity)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals("Project with id: $projectIdentifier not found", responseEntity.body!!.details)
    }

    @Test
    @Order(4)
    fun testCreateTaskWithWrongEnumType() {
        val createTaskCase = BacklogControllerTestCases.getTestCaseInvalidEnumType()
        val projectIdentifier = createTaskCase.keys.first()
        val entity = HttpEntity(createTaskCase[projectIdentifier]!!, headers)
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.postForEntity("$url/$projectIdentifier", entity)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals("Status: ${createTaskCase[projectIdentifier]!!.status} is not valid", responseEntity.body!!.details)
    }

    @Test
    @Order(5)
    fun testUpdateTask() {
        val updateTaskCase = BacklogControllerTestCases.getTestCaseUpdateTask()
        val projectIdentifier = updateTaskCase.keys.first()
        val entity = HttpEntity(updateTaskCase[projectIdentifier]!!, headers)
        val responseEntity: ResponseEntity<Any> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.PUT, entity)
        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    @Test
    @Order(6)
    fun testUpdateTaskWithInvalidStatus() {
        val updateTaskCase = BacklogControllerTestCases.getTestCaseUpdateFailed()
        val projectIdentifier = updateTaskCase.keys.first()
        val entity = HttpEntity(updateTaskCase[projectIdentifier]!!, headers)
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.PUT, entity)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
    }

    @Test
    @Order(7)
    fun deleteTask() {
        val responseEntity: ResponseEntity<Any> = restTemplate.exchange("$url/KOTL1/KOTL1-2", HttpMethod.DELETE, HttpEntity(null, headers))
        print(responseEntity.body)
        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        // Delete again, expect Bad request, task not found
        val responseEntity2: ResponseEntity<ErrorDetails> = restTemplate.exchange("$url/KOTL1/KOTL1-2", HttpMethod.DELETE, HttpEntity(null, headers))
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.statusCode)
        Assertions.assertNotNull(responseEntity2.body)
        Assertions.assertEquals("Task KOTL1-2 not found", responseEntity2.body!!.details)
    }
}