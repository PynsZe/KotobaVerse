package com.kotobaverse.shared.model

import kotlinx.serialization.Serializable

@Serializable
enum class SourceKind {
    SONG,
    TEXT,
    SENTENCE,
    // ANIME, BOOK, FILM, GAME pour plus tard
}
