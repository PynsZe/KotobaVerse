package io.github.pynsze.auth.validation

sealed interface ValidationError {
    val code: String
    val message: String
}

// --- Email ---
data object EmailEmpty : ValidationError {
    override val code = "email.empty"
    override val message = "L'email est requis."
}

data object EmailTooLong : ValidationError {
    override val code = "email.too_long"
    override val message = "L'email ne peut pas dépasser 254 caractères."
}

data object EmailMalformed : ValidationError {
    override val code = "email.malformed"
    override val message = "Format d'email invalide."
}

// --- Username ---
data object UsernameEmpty : ValidationError {
    override val code = "username.empty"
    override val message = "Le nom d'utilisateur est requis."
}

data object UsernameTooShort : ValidationError {
    override val code = "username.too_short"
    override val message = "Le nom d'utilisateur doit faire au moins 3 caractères."
}

data object UsernameTooLong : ValidationError {
    override val code = "username.too_long"
    override val message = "Le nom d'utilisateur ne peut pas dépasser 30 caractères."
}

data object UsernameInvalidChars : ValidationError {
    override val code = "username.invalid_chars"
    override val message =
        "Le nom d'utilisateur doit commencer par une lettre et ne contenir que lettres, chiffres, underscores ou tirets."
}

// --- Password ---
data object PasswordEmpty : ValidationError {
    override val code = "password.empty"
    override val message = "Le mot de passe est requis."
}

data object PasswordTooShort : ValidationError {
    override val code = "password.too_short"
    override val message = "Le mot de passe doit faire au moins 8 caractères."
}

data object PasswordTooLong : ValidationError {
    override val code = "password.too_long"
    override val message = "Le mot de passe ne peut pas dépasser 72 caractères."
}