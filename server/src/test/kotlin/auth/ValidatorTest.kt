package io.github.pynsze.auth.validation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ValidatorsTest {

    @Test fun `email valide passe`() {
        assertNull(EmailValidator.validate("mathis@example.com"))
    }

    @Test fun `email vide echoue`() {
        assertEquals(EmailEmpty, EmailValidator.validate("   "))
    }

    @Test fun `email malforme echoue`() {
        assertEquals(EmailMalformed, EmailValidator.validate("pas-un-email"))
    }

    @Test fun `username valide passe`() {
        assertNull(UsernameValidator.validate("mathis_42"))
    }

    @Test fun `username commencant par chiffre echoue`() {
        assertEquals(UsernameInvalidChars, UsernameValidator.validate("42mathis"))
    }

    @Test fun `username trop court echoue`() {
        assertEquals(UsernameTooShort, UsernameValidator.validate("ab"))
    }

    @Test fun `password 8 chars passe`() {
        assertNull(PasswordValidator.validate("hunter12"))
    }

    @Test fun `password 73 chars echoue (limite BCrypt)`() {
        assertEquals(PasswordTooLong, PasswordValidator.validate("a".repeat(73)))
    }

    @Test fun `registration valide n'a pas d'erreurs`() {
        val r = RegistrationValidator.validate(
            email = "mathis@example.com",
            username = "mathis42",
            password = "hunter12",
        )
        assertTrue(r.isValid)
        assertTrue(r.errors.isEmpty())
    }

    @Test fun `registration invalide collecte toutes les erreurs`() {
        val r = RegistrationValidator.validate(
            email = "nope",
            username = "ab",
            password = "x",
        )
        assertEquals(3, r.errors.size)
    }
}