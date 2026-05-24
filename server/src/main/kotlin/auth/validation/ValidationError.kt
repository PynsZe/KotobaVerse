package io.github.pynsze.auth.validation

sealed interface ValidationError {
    val code: String
    val message: String
}

// --- Email ---
data object EmailEmpty : ValidationError {
    override val code = "email.empty"
    override val message = "Email is required"
}

data object EmailTooLong : ValidationError {
    override val code = "email.too_long"
    override val message = "Email can exceed 254 characters"
}

data object EmailMalformed : ValidationError {
    override val code = "email.malformed"
    override val message = "Email format is invalid"
}

// --- Username ---
data object UsernameEmpty : ValidationError {
    override val code = "username.empty"
    override val message = "Username is required"
}

data object UsernameTooShort : ValidationError {
    override val code = "username.too_short"
    override val message = "Username must contain at least 3 characters"
}

data object UsernameTooLong : ValidationError {
    override val code = "username.too_long"
    override val message = "Username must contain a maximum of 30 characters"
}

data object UsernameInvalidChars : ValidationError {
    override val code = "username.invalid_chars"
    override val message =
        "Username needs to start with a letter and can only contain letters, numbers, dashes and underscores"
}

// --- Password ---
data object PasswordEmpty : ValidationError {
    override val code = "password.empty"
    override val message = "Password is required"
}

data object PasswordTooShort : ValidationError {
    override val code = "password.too_short"
    override val message = "Password needs to be at least 8 characters"
}

data object PasswordTooLong : ValidationError {
    override val code = "password.too_long"
    override val message = "Password can not exceed 72 characters"
}