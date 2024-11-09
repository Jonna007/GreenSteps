package com.greensteps.greensteps.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

data class ActivityDTO(
    val type: String? = null,
    val description: String? = null,
    val carbonEmission: Float? = null,
    val date: LocalDate? = null,
    val userId: Long? = null
)
