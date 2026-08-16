package com.kotobaverse.client.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val YoruColorScheme = darkColorScheme(
    background = YoruPalette.Background,
    onBackground = YoruPalette.TextPrimary,
    surface = YoruPalette.Surface,
    onSurface = YoruPalette.TextPrimary,
    surfaceVariant = YoruPalette.SurfaceVariant,
    onSurfaceVariant = YoruPalette.TextSecondary,
    primary = YoruPalette.Accent,
    onPrimary = YoruPalette.OnAccent,
    primaryContainer = YoruPalette.AccentContainer,
    onPrimaryContainer = YoruPalette.Accent,
    secondary = YoruPalette.PosNoun,
    onSecondary = YoruPalette.Background,
    tertiary = YoruPalette.PublicDomainGreen,
    onTertiary = YoruPalette.Background,
    outline = YoruPalette.Border,
    outlineVariant = YoruPalette.BorderStrong,
)

@Composable
fun KotobaVerseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = YoruColorScheme,
        typography = KotobaVerseTypography,
        content = content,
    )
}
