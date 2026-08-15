package io.github.pynsze.auth

import io.github.pynsze.auth.model.SessionPrincipal
import io.github.pynsze.auth.persistance.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*

fun Application.configureTokenizer() {
    val config = environment.config
    val encryptionKey = hex(config.property("auth.session.encryptionKey").getString())
    val signingKey = hex(config.property("auth.session.signingKey").getString())
    val cookieSecure = config.property("auth.session.cookieSecure").getString().toBoolean()
    val maxAgeSeconds = config.property("auth.session.maxAgeSeconds").getString().toLong()

    val userRepository = UserRepository()

    install(Authentication) {
        session<SessionPrincipal>("session-auth") {
            validate { session ->
                val user = userRepository.findById(session.userId)
                if (user != null && user.isActive) session else null
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }

    routing {
        authenticate("session-auth") {
            post("/tokenize") {
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}