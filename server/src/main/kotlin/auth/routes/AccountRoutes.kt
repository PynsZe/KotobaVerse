package io.github.pynsze.auth.routes

import com.kotobaverse.shared.api.ApiError
import io.github.pynsze.auth.model.SessionPrincipal
import io.github.pynsze.auth.service.AccountService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.accountRoutes(
    accountService: AccountService
) {
    get("/me") {
        val session = call.principal<SessionPrincipal>()!!
        val user = accountService.getMe(id = session.userId)

        if (user == null) {
            call.clearSessionCookie()
            call.respond(
                HttpStatusCode.Unauthorized,
                ApiError("session_stale", "Invalid Session")
            )
            return@get
        }

        call.respond(HttpStatusCode.OK, user.toMeResponse())
    }

    patch("/me/disable") {
        val session = call.principal<SessionPrincipal>()!!
        val updated = accountService.disableMe(session.userId)

        call.clearSessionCookie()

        if (!updated) {
            call.respond(HttpStatusCode.NotFound)
            return@patch
        }

        call.respond(HttpStatusCode.NoContent)
    }

    delete("/me") {
        val session = call.principal<SessionPrincipal>()!!
        val deleted = accountService.deleteMe(session.userId)

        call.clearSessionCookie()

        if (!deleted) {
            call.respond(HttpStatusCode.InternalServerError)
            return@delete
        }

        call.respond(HttpStatusCode.NoContent)
    }
}
