package io.github.pynsze

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.usersRoutes() {
    route("/users") {
        get("") {
            call.respond(HttpStatusCode.OK, mapOf("status" to "ok"))
        }
    }
}
