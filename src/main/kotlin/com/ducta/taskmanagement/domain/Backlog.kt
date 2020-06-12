package com.ducta.taskmanagement.domain

import javax.persistence.*

@Entity
@Table(name = "backlog")
data class Backlog(
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0

) {

        @OneToOne
        @JoinColumn(name = "project_identifier", referencedColumnName = "project_identifier", unique = true)
        var project: Project? = null

        @OneToOne(mappedBy = "backlog", cascade = [CascadeType.ALL])
        var taskCount: TaskCount = TaskCount()

        @OneToMany(
                cascade = [CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH],
                mappedBy = "backlog",
                fetch = FetchType.EAGER
        )
        var tasks: MutableList<Task> = mutableListOf()

        fun addTask(task: Task) {
                tasks.add(task)
        }
}