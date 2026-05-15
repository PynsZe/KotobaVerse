package io.github.pynsze

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    route("/auth") {
        post("/register") {
            call.respond(HttpStatusCode.OK, mapOf("status" to "ok"))
        }
    }
}
