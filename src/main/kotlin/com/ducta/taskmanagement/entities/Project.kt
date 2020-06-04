package com.ducta.taskmanagement.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "project")
data class Project(
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(name = "project_name")
        val projectName: String = "",

        @Column(name = "project_identifier")
        val projectIdentifier: String = "",

        @Column(name = "description")
        val description: String = ""

) {

        @Column(name = "start_date")
        private lateinit var startDate: LocalDateTime

        @Column(name = "end_date")
        private lateinit var endDate: LocalDateTime

        @Column(name = "created_at")
        private lateinit var createdAt: LocalDateTime

        @Column(name = "updated_at")
        private lateinit var updatedAt: LocalDateTime

        @ManyToOne
        @JoinColumn(name = "user_id")
        private lateinit var user: User

        @OneToOne(
                fetch = FetchType.EAGER,
                mappedBy = "project"
        )
        private lateinit var backlog: Backlog
}
