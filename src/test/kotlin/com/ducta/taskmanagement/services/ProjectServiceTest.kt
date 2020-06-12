package com.ducta.taskmanagement.services

import com.ducta.taskmanagement.domain.dto.ProjectDto
import com.ducta.taskmanagement.exceptions.EntityAccessDeniedException
import com.ducta.taskmanagement.exceptions.EntityNotFoundException
import com.ducta.taskmanagement.repositories.ProjectRepository
import com.ducta.taskmanagement.repositories.UserRepository
import com.ducta.taskmanagement.services.impl.ProjectServiceImpl
import com.ducta.taskmanagement.services.testcases.ProjectServiceTestCases
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class ProjectServiceTest {
    @Mock
    private lateinit var projectRepository: ProjectRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var projectService: ProjectServiceImpl

    @BeforeEach
    fun setMockOutput() {
        val existProject = ProjectServiceTestCases.getExistProject()
        val nonExistProject = ProjectServiceTestCases.getNonExistProject()
        val username = ProjectServiceTestCases.getUser().username
        `when`(projectRepository.findById(nonExistProject.projectIdentifier)).thenReturn(Optional.empty())
        `when`(projectRepository.findById(existProject.projectIdentifier)).thenReturn(Optional.of(existProject))
        `when`(projectRepository.findAllProjectsByUsername(username)).thenReturn(ProjectServiceTestCases.getListProjects())
        doNothing().`when`(projectRepository).delete(existProject)
    }

    @DisplayName("Test isUserOwnerOfProject service")
    @Test
    fun testIsUserOwnerOfProject() {
        val username = ProjectServiceTestCases.getUser().username
        val nonExistUser = ProjectServiceTestCases.getNonExistUser()
        val nonExistProjectId = ProjectServiceTestCases.getNonExistProject().projectIdentifier
        val existProjectId = ProjectServiceTestCases.getExistProject().projectIdentifier

        Assertions.assertDoesNotThrow() {
            projectService.isUserOwnerOfProject(nonExistProjectId, nonExistUser)
        }
        val exception = Assertions.assertThrows(EntityAccessDeniedException::class.java) {
            projectService.isUserOwnerOfProject(existProjectId, nonExistUser)
        }
        Assertions.assertEquals(EntityAccessDeniedException().message, exception.message)
        Assertions.assertDoesNotThrow() {
            projectService.isUserOwnerOfProject(existProjectId, username)
        }
    }

    @DisplayName("Test deleteProject service")
    @Test
    fun testDeleteProject() {
        val existProjectId = ProjectServiceTestCases.getExistProject().projectIdentifier
        val nonExistProjectId = ProjectServiceTestCases.getNonExistProject().projectIdentifier
        Assertions.assertDoesNotThrow() {
            projectService.deleteProject(existProjectId)
        }
        val exception = Assertions.assertThrows(EntityNotFoundException::class.java) {
            projectService.deleteProject(nonExistProjectId)
        }
        Assertions.assertEquals(EntityNotFoundException("Project $nonExistProjectId not found").message, exception.message)
    }

    @DisplayName("Test getProjectByProjectIdentifier service")
    @Test
    fun testGetProjectByProjectIdentifier() {
        lateinit var project: ProjectDto
        val expectedExistProject = ProjectServiceTestCases.getExistProject()

        // Case 1: Project exists
        Assertions.assertDoesNotThrow() {
            project = projectService.getProjectByProjectIdentifier(expectedExistProject.projectIdentifier)
        }
        Assertions.assertEquals(expectedExistProject.toDto(), project)

        // Case 2: Project doesn't exist
        val nonExistProjectId = ProjectServiceTestCases.getNonExistProject().projectIdentifier
        val exception = Assertions.assertThrows(EntityNotFoundException::class.java) {
            projectService.getProjectByProjectIdentifier(nonExistProjectId)
        }
        Assertions.assertEquals(EntityNotFoundException("Project $nonExistProjectId not found").message, exception.message)
    }

    @DisplayName("Test getAllProjectsByUsername service")
    @Test
    fun testGetAllProjectByUsername() {
        val username = ProjectServiceTestCases.getUser().username
        val actualProjects = projectService.getAllProjectsByUsername(username)
        Assertions.assertEquals(ProjectServiceTestCases.getListProjectDto(), actualProjects)
    }
}