package com.kotobaverse.client.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val KotobaVerseTypography = Typography(
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 28.sp,
        letterSpacing = (-0.2).sp
    ),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 19.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
    labelSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        letterSpacing = 0.4.sp
    ),
)
