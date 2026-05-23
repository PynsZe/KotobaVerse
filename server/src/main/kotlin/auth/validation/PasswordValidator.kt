package io.github.pynsze.auth.validation

object PasswordValidator {
    private const val MAX_LENGHT = 72 // limite BCrypt
    private const val MIN_LENGHT = 8

    fun validate(password: String): ValidationError? {
        val trimmed = password.trim()

        return when {
            trimmed.isEmpty() -> PasswordEmpty
            trimmed.length < MIN_LENGHT -> PasswordTooShort
            trimmed.length > MAX_LENGHT -> PasswordTooLong
            else -> null
        }
    }

}