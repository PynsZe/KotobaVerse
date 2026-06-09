package io.github.pynsze.auth.logging

import org.slf4j.LoggerFactory

object AuthEventLogger {

    private val logger = LoggerFactory.getLogger("auth.events")

    // --- Registration ---
    fun registrationSuccess(userId: Long, email: String) {
        logger.info("event=auth.registration.success userId={} email={}", userId, email)
    }

    fun registrationValidationFailed(email: String, errorCount: Int) {
        logger.info(
            "event=auth.registration.validation_failed email={} errorCount={}",
            email, errorCount,
        )
    }

    fun registrationConflict(field: String, value: String) {
        logger.info("event=auth.registration.conflict field={} value={}", field, value)
    }

    // --- Login ---
    fun loginSuccess(userId: Long, identifier: String) {
        logger.info("event=auth.login.success userId={} identifier={}", userId, identifier)
    }

    fun loginFailed(identifier: String) {
        logger.info("event=auth.login.failed identifier={}", identifier)
    }

    fun loginAccountDisabled(userId: Long) {
        logger.info("event=auth.login.account_disabled userId={}", userId)
    }

    // --- Logout & Account ---
    fun logout(userId: Long) {
        logger.info("event=auth.logout userId={}", userId)
    }

    fun accountDeactivated(userId: Long) {
        logger.info("event=auth.account.deactivated userId={}", userId)
    }

    fun accountDeletion(userId: Long) {
        logger.info("event=auth.account.deletion userId={}", userId)
    }
}