package com.ducta.taskmanagement.entities

import javax.persistence.*

@Entity
@Table(name = "backlog")
data class Backlog(
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @OneToOne
        @JoinColumn(name = "project_identifier", referencedColumnName = "project_identifier", unique = true)
        var project: Project? = null,

        @OneToOne(mappedBy = "backlog", cascade = [CascadeType.ALL])
        var taskCount: TaskCount = TaskCount(),

        @OneToMany(
                cascade = [CascadeType.ALL],
                mappedBy = "backlog",
                fetch = FetchType.LAZY
        )
        var tasks: MutableSet<Task>? = null
) {
}