package com.greensteps.greensteps.service

import com.greensteps.greensteps.dto.UserDTO
import com.greensteps.greensteps.model.User
import com.greensteps.greensteps.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.greensteps.greensteps.exception.ResourceNotFoundException



@Service
@Transactional
class UserService(private val userRepository: UserRepository) {

    fun findUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun updateUser(id: Long, userDTO: UserDTO): User {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException("User not found") }
        user.name = userDTO.name.toString()
        user.email = userDTO.email.toString()
        return userRepository.save(user)
    }

    fun partiallyUpdateUser(id: Long, userDTO: UserDTO): User {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException("User not found") }
        user.name = userDTO.name.toString()
        user.email = userDTO.email.toString()
        return userRepository.save(user)
    }
}
