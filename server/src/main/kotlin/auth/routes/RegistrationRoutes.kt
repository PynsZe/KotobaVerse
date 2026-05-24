package io.github.pynsze.auth.routes

import com.kotobaverse.shared.api.ApiError
import com.kotobaverse.shared.api.FieldError
import com.kotobaverse.shared.api.dto.RegisterRequest
import io.github.pynsze.auth.service.RegistrationResult
import io.github.pynsze.auth.service.RegistrationService
import io.github.pynsze.auth.validation.RegistrationValidation
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.registrationRoutes(regitrationService: RegistrationService) {
    post("/register") {
        val request = call.receive<RegisterRequest>()

        when (val result = regitrationService.register(
            request.email,
            request.username,
            request.password,
            request.displayName
        )) {
            is RegistrationResult.Success -> {
                call.setSessionCookie(result.user.id)
                call.respond(HttpStatusCode.Created, result.user.toMeResponse())
            }

            is RegistrationResult.ValidationFailed -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiError(
                        code = "validation_failed",
                        message = "All fields are required and need to respect the policy",
                        fieldErrors = result.validation.toFieldErrors()
                    )
                )
            }

            is RegistrationResult.EmailAlreadyInUse -> call.respond(
                HttpStatusCode.Conflict,
                ApiError("email.taken", "This email is already used")
            )

            is RegistrationResult.UsernameAlreadyInUse -> call.respond(
                HttpStatusCode.Conflict,
                ApiError("username.taken", "This username is already used")
            )
        }
    }
}

private fun RegistrationValidation.toFieldErrors() = buildList {
    email?.let { add(FieldError("email", it.code, it.message)) }
    username?.let { add(FieldError("username", it.code, it.message)) }
    password?.let { add(FieldError("password", it.code, it.message)) }
}
