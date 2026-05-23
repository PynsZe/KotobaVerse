package io.github.pynsze.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionPrincipal(
    val userId: Long,
    val createdAt: Long
)
