package io.github.pynsze.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
    routing {
        route("/auth") {
            // Endpoint de vérification de câblage. À supprimer en fin d'étape 8.
            get("/_ping") {
                call.respond(HttpStatusCode.OK, mapOf("module" to "auth", "status" to "wired"))
            }
        }
    }
}