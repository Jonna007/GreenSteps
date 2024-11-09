package com.greensteps.greensteps.dto


import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class UserDTO(
    @field:Size(max = 50, message = "Name must be less than 50 characters")
    val name: String?,

    @field:Email(message = "Invalid email format")
    val email: String?
)

