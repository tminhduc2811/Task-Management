package com.ducta.taskmanagement.entities

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
        val sequence: String = "",

        @Column(name = "summary")
        val summary: String = "",

        @Column(name = "acceptance_criteria")
        val acceptanceCriteria: String = "",

        @Column(name = "status")
        @Enumerated(EnumType.ORDINAL)
        val status: Status = Status.TODO,

        @Column(name = "priority")
        @Enumerated(EnumType.ORDINAL)
        val priority: Priority = Priority.LOW

) {

        @Column(name = "due_date")
        private lateinit var dueDate: LocalDate

        @Column(name = "created_at")
        private lateinit var  createdAt: LocalDateTime

        @Column(name = "updated_at")
        private lateinit var  updatedAt: LocalDateTime

        @ManyToOne
        @JoinColumn(name = "backlog_id")
        private lateinit var  backlog: Backlog

}