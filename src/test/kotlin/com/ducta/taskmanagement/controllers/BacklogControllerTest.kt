package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.controllers.testcases.BacklogControllerTestCases
import com.ducta.taskmanagement.dto.TaskDto
import com.ducta.taskmanagement.exceptions.ErrorDetails
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = ["classpath:application-test.properties"])
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
class BacklogControllerTest {


    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val url = "/api/backlog"

    @Test
    @Order(1)
    fun testCreateTask() {
        val createTaskCase1 = BacklogControllerTestCases.getTestCaseCreateTaskSuccessfully()
        val projectIdentifier = createTaskCase1.keys.first()
        for (task in createTaskCase1[projectIdentifier] ?: error("Project identifier of test not found")) {
            val entity = HttpEntity(task)
            println(entity)
            val responseEntity: ResponseEntity<Any> = restTemplate.postForEntity("$url/$projectIdentifier", entity)
            println(responseEntity.body)
            Assertions.assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        }
        // Get all tasks
        val response: ResponseEntity<List<TaskDto>> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.GET)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(2, response.body!!.size)
        Assertions.assertEquals("KOTL1-1", response.body!![0].sequence)
        Assertions.assertEquals("KOTL1-2", response.body!![1].sequence)
    }

    @Test
    @Order(2)
    fun testCreateTaskWithNonExistedProjectIdentifier() {
        val createTaskCase = BacklogControllerTestCases.getTestCaseCreateTaskFailed()
        val projectIdentifier = createTaskCase.keys.first()
        val entity = HttpEntity(createTaskCase[projectIdentifier] ?: error("Project identifier of test case not found"))
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.postForEntity("$url/$projectIdentifier", entity)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals("Project with id: $projectIdentifier not found", responseEntity.body!!.details)
    }

    @Test
    @Order(3)
    fun testCreateTaskWithWrongEnumType() {
        val createTaskCase = BacklogControllerTestCases.getTestCaseInvalidEnumType()
        val projectIdentifier = createTaskCase.keys.first()
        val entity = HttpEntity(createTaskCase[projectIdentifier] ?: error("Project identifier of test case not found"))
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.postForEntity("$url/$projectIdentifier", entity)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals("Status: ${createTaskCase[projectIdentifier]!!.status} is not valid", responseEntity.body!!.details)
    }

    @Test
    @Order(4)
    fun testUpdateTask() {
        val updateTaskCase = BacklogControllerTestCases.getTestCaseUpdateTask()
        val projectIdentifier = updateTaskCase.keys.first()
        val entity = HttpEntity(updateTaskCase[projectIdentifier]!!)
        val responseEntity: ResponseEntity<Any> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.PUT, entity)
        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    @Test
    @Order(5)
    fun testUpdateTaskWithInvalidStatus() {
        val updateTaskCase = BacklogControllerTestCases.getTestCaseUpdateFailed()
        val projectIdentifier = updateTaskCase.keys.first()
        val entity = HttpEntity(updateTaskCase[projectIdentifier]!!)
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.PUT, entity)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
    }

    @Test
    @Order(6)
    fun deleteTask() {
        val responseEntity: ResponseEntity<Any> = restTemplate.exchange("$url/KOTL1/KOTL1-1", HttpMethod.DELETE)
        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        // Delete again, expect Bad request, task not found
        val responseEntity2: ResponseEntity<ErrorDetails> = restTemplate.exchange("$url/KOTL1/KOTL1-1", HttpMethod.DELETE)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.statusCode)
        Assertions.assertNotNull(responseEntity2.body)
        Assertions.assertEquals("Task KOTL1-1 not found", responseEntity2.body!!.details)
    }
}