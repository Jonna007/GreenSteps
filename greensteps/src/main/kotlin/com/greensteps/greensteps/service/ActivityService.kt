package com.greensteps.greensteps.service

import com.greensteps.greensteps.dto.ActivityDTO
import com.greensteps.greensteps.model.Activity
import com.greensteps.greensteps.repository.ActivityRepository
import com.greensteps.greensteps.repository.UserRepository
import com.greensteps.greensteps.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ActivityService(
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository
) {

    fun saveActivity(activity: Activity): Activity {
        return activityRepository.save(activity)
    }

    fun getActivityById(id: Long): Activity? {
        return activityRepository.findById(id).orElse(null)
    }

    fun getAllActivities(): List<Activity> {
        return activityRepository.findAll()
    }

    fun deleteActivity(id: Long) {
        activityRepository.deleteById(id)
    }

    fun updateActivity(id: Long, activityDTO: ActivityDTO): Activity {
        val activity = activityRepository.findById(id).orElseThrow { ResourceNotFoundException("Activity not found") }


        activity.type = activityDTO.type ?: throw IllegalArgumentException("Type cannot be null for PUT request")
        activity.description = activityDTO.description
        activity.carbonEmission = activityDTO.carbonEmission ?: throw IllegalArgumentException("Carbon emission cannot be null for PUT request")
        activity.date = activityDTO.date ?: throw IllegalArgumentException("Date cannot be null for PUT request")
        activity.user = userRepository.findById(activityDTO.userId ?: throw IllegalArgumentException("User ID cannot be null for PUT request"))
            .orElseThrow { ResourceNotFoundException("User not found") }

        return activityRepository.save(activity)
    }

    fun partiallyUpdateActivity(id: Long, activityDTO: ActivityDTO): Activity {
        val activity = activityRepository.findById(id).orElseThrow { ResourceNotFoundException("Activity not found") }


        activityDTO.type?.let { activity.type = it }
        activityDTO.description?.let { activity.description = it }
        activityDTO.carbonEmission?.let { activity.carbonEmission = it }
        activityDTO.date?.let { activity.date = it }
        activityDTO.userId?.let {
            val user = userRepository.findById(it).orElseThrow { ResourceNotFoundException("User not found") }
            activity.user = user
        }

        return activityRepository.save(activity)
    }
}
