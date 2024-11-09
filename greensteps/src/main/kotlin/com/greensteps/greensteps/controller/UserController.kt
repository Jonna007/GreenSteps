package com.greensteps.greensteps.controller

import com.greensteps.greensteps.dto.UserDTO
import com.greensteps.greensteps.model.User
import com.greensteps.greensteps.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import java.time.LocalDate

@RestController
@RequestMapping("/api/users")
@Validated
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@Valid @RequestBody userDTO: UserDTO): ResponseEntity<Map<String, Any?>> {
        val user = userDTO.name?.let {
            userDTO.email?.let { it1 ->
                User(
                    name = it,
                    email = it1,
                    registrationDate = LocalDate.now()
                )
            }
        }
        val savedUser = user?.let { userService.saveUser(it) }
        return ResponseEntity.status(HttpStatus.CREATED).body(
            mapOf("status" to "success", "data" to savedUser)
        )
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(mapOf("status" to "success", "data" to user))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("status" to "fail", "message" to "User not found"))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        return if (userService.getUserById(id) != null) {
            userService.deleteUser(id)
            ResponseEntity.ok(mapOf("status" to "success"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("status" to "fail", "message" to "User not found"))
        }
    }


    @GetMapping
    fun getAllUsers(): ResponseEntity<Map<String, Any>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(mapOf("status" to "success", "data" to users))
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody userDTO: UserDTO): ResponseEntity<Map<String, Any>> {
        val updatedUser = userService.updateUser(id, userDTO)
        return ResponseEntity.ok(mapOf("status" to "success", "data" to updatedUser))
    }

    @PatchMapping("/{id}")
    fun partiallyUpdateUser(@PathVariable id: Long, @RequestBody userDTO: UserDTO): ResponseEntity<Map<String, Any>> {
        val updatedUser = userService.partiallyUpdateUser(id, userDTO)
        return ResponseEntity.ok(mapOf("status" to "success", "data" to updatedUser))
    }

}
