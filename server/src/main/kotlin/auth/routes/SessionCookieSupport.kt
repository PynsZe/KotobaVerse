package io.github.pynsze.auth.routes

import io.github.pynsze.auth.model.SessionPrincipal
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun ApplicationCall.setSessionCookie(userId: Long) {
    sessions.set(
        SessionPrincipal(
            userId = userId,
            createdAt = System.currentTimeMillis()
        )
    )
}

fun ApplicationCall.clearSessionCookie() {
    sessions.clear<SessionPrincipal>()
}