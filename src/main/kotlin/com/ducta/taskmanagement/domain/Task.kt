package com.ducta.taskmanagement.domain

import com.ducta.taskmanagement.domain.dto.TaskCreateDto
import com.ducta.taskmanagement.domain.dto.TaskDto
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "task")
data class Task(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long = 0,

        @Column(name = "sequence")
        var sequence: String = "",

        @Column(name = "summary")
        val summary: String = "",

        @Column(name = "acceptance_criteria")
        val acceptanceCriteria: String = "",

        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        val status: Status = Status.TODO,

        @Column(name = "priority")
        @Enumerated(EnumType.STRING)
        val priority: Priority = Priority.LOW,

        @Column(name = "due_date")
        var dueDate: LocalDate? = null,

        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        var updatedAt: LocalDateTime = LocalDateTime.now()


) {

    @ManyToOne
    @JoinColumn(name = "backlog_id")
    var backlog: Backlog? = null

    fun toDto() = TaskDto(
            id = id,
            sequence = sequence,
            summary = summary,
            acceptanceCriteria = acceptanceCriteria,
            status = status.ordinal,
            priority = priority.ordinal,
            dueDate = dueDate,
            createdAt = createdAt,
            updatedAt = updatedAt,
            backlogId = backlog!!.id
    )

    companion object {
        fun fromDto(taskCreateDto: TaskCreateDto) = Task(
                summary = taskCreateDto.summary,
                acceptanceCriteria = taskCreateDto.acceptanceCriteria,
                status = Status.values()[taskCreateDto.priority],
                priority = Priority.values()[taskCreateDto.priority],
                dueDate = setDate(taskCreateDto.dueDate)
        )
        fun setDate(date: String?): LocalDate? {
            if (date.isNullOrBlank()) {
                return null
            }
            return LocalDate.parse(date)
        }
    }
}