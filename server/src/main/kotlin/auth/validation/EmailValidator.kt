package io.github.pynsze.auth.validation

object EmailValidator {

    private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private const val MAX_LENGHT = 254

    fun validate(email: String): ValidationError? {
        val trimmed = email.trim()
        return when {
            trimmed.isEmpty() -> EmailEmpty
            trimmed.length > MAX_LENGHT -> EmailTooLong
            !EMAIL_REGEX.matches(trimmed) -> EmailMalformed
            else -> null
        }
    }
}