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
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.auth.session
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.sessions.SessionTransportTransformerEncrypt
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.util.hex

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
            get("/_ping") {
                call.respond(HttpStatusCode.OK, mapOf("module" to "auth", "status" to "wired"))
            }

            registrationRoutes(registrationService)

            sessionRoutes(loginService)

            authenticate("session-auth") {
                get("/_authed_ping") {
                    val session = call.principal<SessionPrincipal>()
                    call.respond(HttpStatusCode.OK, mapOf("userId" to session!!.userId.toString()))
                }

                accountRoutes(accountService)
            }
        }
    }
}