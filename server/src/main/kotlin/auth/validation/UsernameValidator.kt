package io.github.pynsze.auth.validation

object UsernameValidator {

    private const val MIN_LENGHT = 3
    private const val MAX_LENGHT = 25
    private val USERNAME_REGEX = Regex("^[A-Za-z][A-Za-z0-9-_]*$")

    fun validate(username: String): ValidationError? {

        val trimmed = username.trim()
        return when {
            trimmed.isEmpty() -> UsernameEmpty
            trimmed.length < MIN_LENGHT -> UsernameTooShort
            trimmed.length > MAX_LENGHT -> UsernameTooLong
            !trimmed.matches(USERNAME_REGEX) -> UsernameInvalidChars
            else -> null
        }
    }

}