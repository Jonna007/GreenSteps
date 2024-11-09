package com.greensteps.greensteps.controller

import com.greensteps.greensteps.service.ActivityService
import com.greensteps.greensteps.dto.ActivityDTO
import com.greensteps.greensteps.model.Activity
import com.greensteps.greensteps.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/activities")
@Validated
class ActivityController(
    private val activityService: ActivityService,
    private val userService: UserService
) {

    @PostMapping
    fun createActivity(@Valid @RequestBody activityDTO: ActivityDTO): ResponseEntity<Map<String, Any?>> {
        val user = activityDTO.userId?.let { userService.getUserById(it) }
        return if (user != null) {
            val activity = Activity(
                type = activityDTO.type ?: throw IllegalArgumentException("Type is required"),
                description = activityDTO.description,
                carbonEmission = activityDTO.carbonEmission ?: throw IllegalArgumentException("Carbon emission is required"),
                date = activityDTO.date ?: throw IllegalArgumentException("Date is required"),
                user = user
            )
            val savedActivity = activityService.saveActivity(activity)
            ResponseEntity.status(HttpStatus.CREATED).body(
                mapOf("status" to "success", "data" to savedActivity)
            )
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf("status" to "fail", "message" to "User not found")
            )
        }
    }

    @GetMapping("/{id}")
    fun getActivityById(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        val activity = activityService.getActivityById(id)
        return if (activity != null) {
            ResponseEntity.ok(mapOf("status" to "success", "data" to activity))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                mapOf("status" to "fail", "message" to "Activity not found")
            )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteActivity(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        return if (activityService.getActivityById(id) != null) {
            activityService.deleteActivity(id)
            ResponseEntity.ok(mapOf("status" to "success"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                mapOf("status" to "fail", "message" to "Activity not found")
            )
        }
    }

    @GetMapping
    fun getAllActivities(): ResponseEntity<Map<String, Any>> {
        val activities = activityService.getAllActivities()
        return ResponseEntity.ok(mapOf("status" to "success", "data" to activities))
    }

    @PutMapping("/{id}")
    fun updateActivity(@PathVariable id: Long, @Valid @RequestBody activityDTO: ActivityDTO): ResponseEntity<Map<String, Any>> {
        val updatedActivity = activityService.updateActivity(id, activityDTO)
        return ResponseEntity.ok(mapOf("status" to "success", "data" to updatedActivity))
    }

    @PatchMapping("/{id}")
    fun partiallyUpdateActivity(@PathVariable id: Long, @RequestBody activityDTO: ActivityDTO): ResponseEntity<Map<String, Any>> {
        val updatedActivity = activityService.partiallyUpdateActivity(id, activityDTO)
        return ResponseEntity.ok(mapOf("status" to "success", "data" to updatedActivity))
    }
}
