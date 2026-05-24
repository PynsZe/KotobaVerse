package io.github.pynsze.auth.routes

import com.kotobaverse.shared.api.ApiError
import com.kotobaverse.shared.api.dto.LoginRequest
import io.github.pynsze.auth.service.LoginResult
import io.github.pynsze.auth.service.LoginService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.sessionRoutes(loginService: LoginService) {
    post("/login") {
        val request = call.receive<LoginRequest>()

        when (val result = loginService.login(request.identifier, request.password)) {
            is LoginResult.Success -> {
                call.setSessionCookie(result.user.id)
                call.respond(
                    HttpStatusCode.OK,
                    result.user.toMeResponse()
                )
            }

            is LoginResult.AccountDisabled -> call.respond(
                HttpStatusCode.Unauthorized,
                ApiError(
                    code = "account_disabled",
                    message = "Your account has been disabled, if you think this is an error, please contact the support"
                )
            )

            is LoginResult.InvalideCredentials -> call.respond(
                HttpStatusCode.Unauthorized,
                ApiError(
                    code = "invalid_credentials",
                    message = "Invalid credentials"
                )
            )

        }
    }

    post("/logout") {
        call.clearSessionCookie()
        call.respond(HttpStatusCode.NoContent)
    }
}