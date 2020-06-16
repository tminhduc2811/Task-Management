package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.controllers.testcases.ProjectControllerTestCases
import com.ducta.taskmanagement.domain.dto.AuthenticatedUserDto
import com.ducta.taskmanagement.domain.dto.ProjectDto
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
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ProjectControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val url = "/api/projects"
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
    fun testGetAllProjects() {
        print(headers)
        val responseEntity: ResponseEntity<List<ProjectDto>> = restTemplate.exchange(url, HttpMethod.GET, HttpEntity(null, headers))
        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals(1, responseEntity.body!!.size)
    }

    @Test
    @Order(3)
    fun testAddExistedProject() {
        val createProjectDto = ProjectControllerTestCases.getTestCaseAddExistedProject()
        val httpEntity = HttpEntity(createProjectDto, headers)

        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.postForEntity(url, httpEntity)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals("Project Identifier already existed", responseEntity.body!!.details)
    }

    @Test
    @Order(4)
    fun testAddNonExistedProject() {
        val projectCreateDto = ProjectControllerTestCases.getTestCaseAddNonExistedProject()
        val httpEntity = HttpEntity(projectCreateDto, headers)
        val responseEntity:ResponseEntity<Any> = restTemplate.postForEntity(url, httpEntity)
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        val responseEntity2: ResponseEntity<ProjectDto> = restTemplate.exchange("$url/${projectCreateDto.projectIdentifier}", HttpMethod.GET, httpEntity)
        Assertions.assertNotNull(responseEntity2.body)
        Assertions.assertEquals(projectCreateDto.projectIdentifier, responseEntity2.body!!.projectIdentifier)
    }

    @Test
    @Order(5)
    fun testDeleteExistedProject() {
        val projectIdentifier = ProjectControllerTestCases.getTestCaseDeleteExistedProject()
        val response: ResponseEntity<Any> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.DELETE, HttpEntity(null, headers))
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        // Try to retrieve it again
        val responseEntity: ResponseEntity<Any> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.GET, HttpEntity(null, headers))
        println(responseEntity.body)
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
    }

    @Test
    @Order(6)
    fun testDeleteNonExistedProject() {
        val projectIdentifier = ProjectControllerTestCases.getTestCaseDeleteNonExistedProject()
        val response: ResponseEntity<Any> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.DELETE, HttpEntity(null, headers))
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
    }

    @Test
    @Order(7)
    fun testUpdateExistedProject() {
        val request = ProjectControllerTestCases.getTestCaseUpdateExistedProject()
        val projectIdentifier = request.keys.first()
        val newProject = request[projectIdentifier]

        val entity = HttpEntity(newProject!!, headers)

        val response: ResponseEntity<Any> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.PUT, entity)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        // Try to retrieve and check again
        val responseEntity: ResponseEntity<ProjectDto> = restTemplate.exchange("$url/$projectIdentifier", HttpMethod.GET, entity)
        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        val actual = responseEntity.body!!
        Assertions.assertEquals(newProject.description, actual.description)
        Assertions.assertEquals(newProject.projectName, actual.projectName)
        Assertions.assertEquals(newProject.startDate, actual.startDate.toString())
        Assertions.assertEquals(newProject.endDate, actual.endDate.toString())
    }

    @Test
    @Order(8)
    fun testCreateProjectWithBadDate() {
        val projectDto = ProjectControllerTestCases.getTestCaseProjectInvalidDate()
        val httpEntity = HttpEntity(projectDto, headers)

        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.postForEntity(url, httpEntity)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals("Validation failed", responseEntity.body!!.details)
        Assertions.assertEquals("Invalid date, start date must be before end date", responseEntity.body!!.errors?.get("startDate"))

    }
}