package com.kotobaverse.shared.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String,
    val displayName: String? = null
)

@Serializable
data class MeResponse(
    val id: Long,
    val email: String,
    val username: String,
    val displayName: String?,
    val isAdmin: Boolean
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)


