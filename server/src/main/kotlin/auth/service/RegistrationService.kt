package io.github.pynsze.auth.service

import io.github.pynsze.auth.logging.AuthEventLogger
import io.github.pynsze.auth.model.User
import io.github.pynsze.auth.persistance.UserRepository
import io.github.pynsze.auth.validation.RegistrationValidation
import io.github.pynsze.auth.validation.RegistrationValidator

sealed interface RegistrationResult {
    data class Success(val user: User) : RegistrationResult
    data class ValidationFailed(val validation: RegistrationValidation) : RegistrationResult
    data object EmailAlreadyInUse : RegistrationResult
    data object UsernameAlreadyInUse : RegistrationResult
}

class RegistrationService(
    private val userRepository: UserRepository,
    private val passwordHasher: BCryptPasswordHasher
) {
    suspend fun register(
        email: String,
        username: String,
        password: String,
        displayName: String? = null
    ): RegistrationResult {
        val validation = RegistrationValidator.validate(email, username, password)
        if (!validation.isValid) {
            AuthEventLogger.registrationValidationFailed(email, validation.errors.size)
            return RegistrationResult.ValidationFailed(validation)
        }

        val normalizedEmail = email.trim().lowercase()
        val trimmedUsername = username.trim()

        if (userRepository.existsByEmail(email)) {
            AuthEventLogger.registrationConflict("email", normalizedEmail)
            return RegistrationResult.EmailAlreadyInUse
        }
        if (userRepository.existsByUsername(username)) {
            AuthEventLogger.registrationConflict("username", username)
            return RegistrationResult.UsernameAlreadyInUse
        }

        val hashedPassword = passwordHasher.hash(password)

        val user = userRepository.insertLocal(
            UserRepository.NewLocalUser(
                email = normalizedEmail,
                username = trimmedUsername,
                passwordHash = hashedPassword,
                displayName = displayName?.trim()?.takeIf { it.isNotEmpty() }
            )
        )
        AuthEventLogger.registrationSuccess(user.id, user.email)
        return RegistrationResult.Success(user)
    }
}
