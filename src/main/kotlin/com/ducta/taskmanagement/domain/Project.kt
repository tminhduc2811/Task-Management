package com.ducta.taskmanagement.domain

import com.ducta.taskmanagement.domain.dto.ProjectCreateDto
import com.ducta.taskmanagement.domain.dto.ProjectDto
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "project")
data class Project(

        @Id
        @Column(name = "project_identifier")
        val projectIdentifier: String = "",

        @Column(name = "project_name")
        val projectName: String = "",

        @Column(name = "description")
        val description: String = "",

        @Column(name = "start_date")
        val startDate: LocalDate? = null,

        @Column(name = "end_date")
        val endDate: LocalDate? = null,

        @Column(name = "created_at")
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime = LocalDateTime.now()


) {

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH])
    @JoinColumn(name = "user_id")
    var user: User? = null

    @OneToOne(
            fetch = FetchType.EAGER,
            mappedBy = "project",
            cascade = [CascadeType.ALL]
    )
    var backlog: Backlog? = null

    fun toDto() = ProjectDto(
            projectName = projectName,
            projectIdentifier = projectIdentifier,
            description = description,
            updatedAt = updatedAt,
            createdAt = createdAt,
            startDate = startDate,
            endDate = endDate,
            userId = user!!.id
    )

    companion object {
        fun fromDto(dto: ProjectCreateDto) = Project(
                projectName = dto.projectName,
                projectIdentifier = dto.projectIdentifier,
                description = dto.description,
                startDate = setDate(dto.startDate),
                endDate = setDate(dto.endDate),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
        )

        fun setDate(date: String?): LocalDate? {
            if (date.isNullOrBlank()) {
                return null
            }
            return LocalDate.parse(date)
        }
    }
}
