package io.github.pynsze.auth.service

import io.github.pynsze.auth.model.User
import io.github.pynsze.auth.persistance.UserRepository

sealed interface LoginResult {
    data class Success(val user: User) : LoginResult
    data object InvalideCredentials : LoginResult
    data object AccountDisabled : LoginResult
}

class LoginService(
    private val userRepository: UserRepository,
    private val passwordHasher: BCryptPasswordHasher
) {
    // dummyHash created by lazy so it is computed once per service life cycle
    private val dummyHash: String by lazy {
        passwordHasher.hash("dymmyPassword")
    }

    suspend fun login(identifier: String, password: String): LoginResult {
        val trimmed = identifier.trim()

        // if identifier contains a "@", the user tries to connect with his email. Else he's using his username (that can't contain "@" symbol)
        val user = if (trimmed.contains("@")) {
            userRepository.findByEmail(identifier)
        } else {
            userRepository.findByUsername(identifier)
        }

        if (user == null) {
            passwordHasher.verify(password, dummyHash)
            return LoginResult.InvalideCredentials
        }

        // if user uses OAuth
        if (user.passwordHash == null) {
            passwordHasher.verify(password, dummyHash)
            return LoginResult.InvalideCredentials
        }

        if (!passwordHasher.verify(password, user.passwordHash))
            return LoginResult.InvalideCredentials

        // Dissociate InvalideCredential and AccountDisable :
        // A user with a disabled account needs to know it.
        // Don't prevent timing attack, but if the attacker got this result,
        // this mean he already has all the user credentials
        // and there's nothing left to protect.
        if (!user.isActive)
            return LoginResult.AccountDisabled

        return LoginResult.Success(user)
    }
}