package com.ducta.taskmanagement.entities

import javax.persistence.*

@Entity
@Table(name = "task_count")
data class TaskCount(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long? = null,

        @Column(name = "total")
        var total: Long = 0,

        @OneToOne
        @JoinColumn(name = "backlog_id", referencedColumnName = "id")
        var backlog: Backlog? = null
) {


}
