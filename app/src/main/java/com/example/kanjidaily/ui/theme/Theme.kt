package com.example.kanjidaily.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFFD95F45),
    secondary = Color(0xFF7A5C52),
    background = Color(0xFFFFF7F0),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFF6E6DD)
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = Color(0xFFFFB39F),
    secondary = Color(0xFFE7B9AA),
    background = Color(0xFF181412),
    surface = Color(0xFF241D1A),
    surfaceVariant = Color(0xFF3A2D29)
)

@Composable
fun KanjiDailyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
