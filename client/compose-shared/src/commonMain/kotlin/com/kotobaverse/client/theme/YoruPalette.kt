package com.kotobaverse.client.theme

import androidx.compose.ui.graphics.Color

object YoruPalette {
    // Fonds
    val Background = Color(0xFF16171C)
    val Surface = Color(0xFF1D1F27)
    val SurfaceVariant = Color(0xFF20222A)
    val SurfaceElevated = Color(0xFF23252D)
    val SurfaceSunken = Color(0xFF1B1D24)
    val Border = Color(0xFF2A2C34)
    val BorderStrong = Color(0xFF33353E)

    // Texte
    val TextPrimary = Color(0xFFE9E3D6)
    val TextPrimaryHigh = Color(0xFFF0EBDF) // kanji/tokens en forte emphase
    val TextSecondary = Color(0xFF8A877F)
    val TextTertiary = Color(0xFF6F6C64)
    val TextMuted = Color(0xFF5F5D57) // particules / tokens non focus

    // Accent (ambre) — CTA primaire, états actifs, avatar
    val Accent = Color(0xFFE0A04A)
    val OnAccent = Color(0xFF16171C)
    val AccentContainer = Color(0xFF312715)

    // Sémantique
    val PublicDomainGreen = Color(0xFF8FC97E)
    val PublicDomainGreenBg = Color(0xFF23311F)

    // Accents grammaticaux (nature/POS) — variantes assombries pour contraste sur fond sombre.
    // cf. légende globale des 3 directions pour les teintes "light" équivalentes.
    val PosNoun = Color(0xFF6FA8E0)
    val PosNounBg = Color(0xFF1B2839)
    val PosVerb = Color(0xFFE3886F)
    val PosAdjective = Color(0xFFB07B2E)
    val PosPreNoun = Color(0xFF9CC06A)
    val PosParticle = Color(0xFF8A8F96)
}
