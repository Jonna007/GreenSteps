package com.greensteps.greensteps.model

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "activity")
data class Activity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var type: String,

    var description: String? = null,

    @Column(name = "carbon_emission", nullable = false)
    var carbonEmission: Float,

    @Column(nullable = false)
    var date: LocalDate,

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    var user: User
)
