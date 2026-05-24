package io.github.pynsze.auth.validation

data class RegistrationValidation(
    val email: ValidationError? = null,
    val username: ValidationError? = null,
    val password: ValidationError? = null,
) {
    val isValid: Boolean get() = email == null && username == null && password == null
    val errors: List<ValidationError> get() = listOfNotNull(email, username, password)
}

object RegistrationValidator {
    fun validate(email: String, username: String, password: String) = RegistrationValidation(
        email = EmailValidator.validate(email),
        username = UsernameValidator.validate(username),
        password = PasswordValidator.validate(password)
    )
}
