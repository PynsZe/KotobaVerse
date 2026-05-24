package io.github.pynsze.auth.service

import io.github.pynsze.auth.model.User
import io.github.pynsze.auth.persistance.UserRepository

class AccountService(
    private val userRepository: UserRepository
) {
    suspend fun getMe(id: Long): User? = userRepository.findById(id)

    suspend fun disableMe(id: Long): Boolean = userRepository.setActive(id, false)

    suspend fun deleteMe(id: Long): Boolean = userRepository.deleteById(id)
}