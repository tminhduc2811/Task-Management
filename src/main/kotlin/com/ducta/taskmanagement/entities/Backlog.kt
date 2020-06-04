package com.ducta.taskmanagement.entities

import javax.persistence.*

@Entity
@Table(name = "backlog")
data class Backlog(
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @OneToOne
        @JoinColumn(name = "project_identifier", referencedColumnName = "project_identifier")
        val project: Project = Project(),

        @OneToOne(mappedBy = "backlog")
        val taskCount: TaskCount = TaskCount(),

        @OneToMany(
                cascade = [CascadeType.ALL],
                mappedBy = "backlog",
                fetch = FetchType.LAZY
        )
        val tasks: MutableSet<Task>? = null
) {
}