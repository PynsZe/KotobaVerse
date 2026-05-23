package io.github.pynsze

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HealthRouteTest {

    @Test
    fun `GET health returns 200 with status ok`() = testApplication {
        application {
            configureSerialization()
            configureRouting()
        }

        val response = client.get("api/v0/health")

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("\"status\""), "body should contain status key, got: $body")
        assertTrue(body.contains("\"ok\""), "body should contain ok value, got: $body")
    }
}
