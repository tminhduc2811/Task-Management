package com.ducta.taskmanagement.controllers

import com.ducta.taskmanagement.controllers.testcases.UserControllerTestCases
import com.ducta.taskmanagement.dto.UserDto
import com.ducta.taskmanagement.exceptions.ErrorDetails
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = ["classpath:application-test.properties"])
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class UserControllerTest {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val url = "/api/users"

    @Test
    @Order(1)
    fun testLoginUserFailed() {
        val userCredentials = UserControllerTestCases.getTestCaseForLoginFailed()
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.postForEntity("$url/login", HttpEntity(userCredentials))
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.statusCode)
    }

    @Test
    @Order(2)
    fun testGetUserByUsername() {
        val responseEntityFailed: ResponseEntity<ErrorDetails> = restTemplate.exchange("$url/nonExistedUsername", HttpMethod.GET)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntityFailed.statusCode)
        Assertions.assertNotNull(responseEntityFailed.body)
        Assertions.assertEquals("Username: nonExistedUsername not found", responseEntityFailed.body!!.details)

        val responseEntitySuccess: ResponseEntity<UserDto> = restTemplate.exchange("$url/admin", HttpMethod.GET)
        Assertions.assertEquals(HttpStatus.OK, responseEntitySuccess.statusCode)
    }

    @Test
    @Order(3)
    fun testRegisterUsernameAlreadyExisted() {
        val userRegisterDto = UserControllerTestCases.getTestCaseRegisterUsernameAlreadyExisted()
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.exchange("$url/register", HttpMethod.POST, HttpEntity(userRegisterDto))
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals("Username: ${userRegisterDto.username} already exists", responseEntity.body!!.details)
    }

    @Test
    @Order(4)
    fun testRegisterEmailAlreadyExisted() {
        val userRegisterDto = UserControllerTestCases.getTestCaseRegisterEmailAlreadyExisted()
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.exchange("$url/register", HttpMethod.POST, HttpEntity(userRegisterDto))
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
        Assertions.assertEquals("Email: ${userRegisterDto.email} already exists", responseEntity.body!!.details)
    }

    @Test
    @Order(5)
    fun testRegisterWithWeakPassword() {
        val userRegisterDto = UserControllerTestCases.getTestCasePasswordNotStrongEnough()
        val responseEntity: ResponseEntity<ErrorDetails> = restTemplate.exchange("$url/register", HttpMethod.POST, HttpEntity(userRegisterDto))
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        Assertions.assertEquals("Validation failed", responseEntity.body!!.details)
        Assertions.assertEquals("Your password is not satisfied", responseEntity.body!!.errors?.get("password"))
    }

    @Test
    @Order(6)
    fun testRegisterUserSuccess() {
        val userRegisterDto = UserControllerTestCases.getTestCaseRegisterUser()
        val responseEntity: ResponseEntity<Void> = restTemplate.exchange("$url/register", HttpMethod.POST, HttpEntity(userRegisterDto))
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        // Check once again
        val responseEntity2: ResponseEntity<UserDto> = restTemplate.getForEntity("$url/${userRegisterDto.username}")
        Assertions.assertEquals(HttpStatus.OK, responseEntity2.statusCode)
        Assertions.assertNotNull(responseEntity2.body)
        Assertions.assertEquals(userRegisterDto.fullName, responseEntity2.body!!.fullName)
    }
}