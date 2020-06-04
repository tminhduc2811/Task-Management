package com.ducta.taskmanagement.entities

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = 0,

        @Column(name = "username")
        val username: String = "",

        @Column(name = "email")
        val email: String = "",

        @Column(name = "password")
        val password: String = "",

        @Column(name = "full_name")
        val fullName: String = "",

        @Column(name = "avatar", nullable = true)
        val avatar: String? = null,

        @OneToMany(
                cascade = [CascadeType.ALL],
                mappedBy = "user",
                fetch = FetchType.LAZY
        )
        val projects: MutableSet<Project>? = null
) {
}