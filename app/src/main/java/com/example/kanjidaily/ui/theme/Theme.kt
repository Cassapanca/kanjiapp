package com.example.kanjidaily.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFFD86A4F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD0),
    onPrimaryContainer = Color(0xFF3B0A03),
    secondary = Color(0xFF8C624F),
    secondaryContainer = Color(0xFFFFDCCB),
    onSecondaryContainer = Color(0xFF321206),
    background = Color(0xFFFFF8F1),
    onBackground = Color(0xFF231917),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF231917),
    surfaceVariant = Color(0xFFF4E4DA),
    onSurfaceVariant = Color(0xFF665B55),
    errorContainer = Color(0xFFFFDAD6)
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = Color(0xFFFFB6A3),
    onPrimary = Color(0xFF5B1809),
    primaryContainer = Color(0xFF7C2D1B),
    onPrimaryContainer = Color(0xFFFFDAD0),
    secondary = Color(0xFFE8BEAA),
    secondaryContainer = Color(0xFF704936),
    onSecondaryContainer = Color(0xFFFFDCCB),
    background = Color(0xFF121416),
    onBackground = Color(0xFFF2EDE9),
    surface = Color(0xFF1D2023),
    onSurface = Color(0xFFF2EDE9),
    surfaceVariant = Color(0xFF3A3330),
    onSurfaceVariant = Color(0xFFD6C4BA),
    errorContainer = Color(0xFF8C1D18)
)

@Composable
fun KanjiDailyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
