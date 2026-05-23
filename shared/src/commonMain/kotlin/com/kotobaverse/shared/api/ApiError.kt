package com.kotobaverse.shared.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: String,
    val message: String,
    val fieldErrors: List<FieldError> = emptyList()
)

@Serializable
data class FieldError(
    val field: String,
    val code: String,
    val message: String
)
