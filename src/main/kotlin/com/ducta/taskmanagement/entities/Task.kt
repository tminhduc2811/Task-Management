package com.ducta.taskmanagement.entities

import com.ducta.taskmanagement.dto.TaskCreateDto
import com.ducta.taskmanagement.dto.TaskDto
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
        var dueDate: LocalDate = LocalDate.now(),

        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        var updatedAt: LocalDateTime = LocalDateTime.now(),

        @ManyToOne
        @JoinColumn(name = "backlog_id")
        var backlog: Backlog? = null

) {

    fun toDto() = TaskDto(
            id = id,
            sequence = sequence,
            summary = summary,
            acceptanceCriteria = acceptanceCriteria,
            status = status.toString(),
            priority = priority.toString(),
            dueDate = dueDate,
            createdAt = createdAt,
            updatedAt = updatedAt,
            backlogId = backlog!!.id
    )

    companion object {
            fun fromDto(taskCreateDto: TaskCreateDto) = Task(
                    summary = taskCreateDto.summary,
                    acceptanceCriteria = taskCreateDto.acceptanceCriteria,
                    status = Status.valueOf(taskCreateDto.status),
                    priority = Priority.valueOf(taskCreateDto.priority),
                    dueDate = taskCreateDto.dueDate
            )
    }
}