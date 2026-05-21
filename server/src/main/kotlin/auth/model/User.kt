package io.github.pynsze.auth.model

import kotlin.time.Instant

data class User(
    val id: Long,
    val email: String,
    val username: String,
    val displayName: String?,
    val passwordHash: String?,
    val oauthProvider: String?,
    val oauthSubject: String?,
    val avatarUrl: String?,
    val isAdmin: Boolean,
    val isActive: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
)