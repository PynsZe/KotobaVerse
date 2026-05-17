package com.kotobaverse.shared.model

import kotlinx.serialization.Serializable

@Serializable
enum class LearningStatus {
    LEARNING,
    KNOWN,
    MATURE,
}
