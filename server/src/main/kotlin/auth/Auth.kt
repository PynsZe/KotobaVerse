package io.github.pynsze.auth

import io.github.pynsze.auth.model.SessionPrincipal
import io.github.pynsze.auth.persistance.UserRepository
import io.github.pynsze.auth.routes.accountRoutes
import io.github.pynsze.auth.routes.registrationRoutes
import io.github.pynsze.auth.routes.sessionRoutes
import io.github.pynsze.auth.service.AccountService
import io.github.pynsze.auth.service.BCryptPasswordHasher
import io.github.pynsze.auth.service.LoginService
import io.github.pynsze.auth.service.RegistrationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*

/**
 * Installe le module d'authentification.
 * Appelé depuis Application.kt après les plugins globaux (ContentNegotiation, CORS, etc.).
 *
 * Étapes ultérieures viendront enrichir ce module :
 *  - Étape 5 : install(Sessions) + install(Authentication)
 *  - Étape 6 : POST /auth/register
 *  - Étape 7 : POST /auth/login + POST /auth/logout
 *  - Étape 8 : GET /auth/me
 */
fun Application.configureAuth() {
    val config = environment.config
    val encryptionKey = hex(config.property("auth.session.encryptionKey").getString())
    val signingKey = hex(config.property("auth.session.signingKey").getString())
    val cookieSecure = config.property("auth.session.cookieSecure").getString().toBoolean()
    val maxAgeSeconds = config.property("auth.session.maxAgeSeconds").getString().toLong()

    val userRepository = UserRepository()
    val passwordHasher = BCryptPasswordHasher()
    val registrationService = RegistrationService(
        userRepository = userRepository,
        passwordHasher = passwordHasher
    )
    val loginService = LoginService(
        userRepository = userRepository,
        passwordHasher = passwordHasher
    )
    val accountService = AccountService(
        userRepository = userRepository
    )

    install(Sessions) {
        cookie<SessionPrincipal>("kotoba_session") {
            cookie.path = "/"
            cookie.httpOnly = true
            cookie.secure = cookieSecure
            cookie.extensions["SameSite"] = "Lax"
            cookie.maxAgeInSeconds = maxAgeSeconds

            transform(SessionTransportTransformerEncrypt(encryptionKey, signingKey))
        }
    }

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
        route("/auth") {

            registrationRoutes(registrationService)
            sessionRoutes(loginService)

            authenticate("session-auth") {

                accountRoutes(accountService)
            
            }
        }
    }
}