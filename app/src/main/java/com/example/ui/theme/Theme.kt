package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = KhidmatiPrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = KhidmatiLightGreen,
    onPrimaryContainer = KhidmatiDarkGreen,
    secondary = KhidmatiDarkGreen,
    onSecondary = Color.White,
    secondaryContainer = KhidmatiSurfaceVariant,
    onSecondaryContainer = KhidmatiTextPrimary,
    tertiary = KhidmatiAmber,
    onTertiary = Color.White,
    background = KhidmatiBackground,
    onBackground = KhidmatiTextPrimary,
    surface = KhidmatiSurface,
    onSurface = KhidmatiTextPrimary,
    surfaceVariant = KhidmatiSurfaceVariant,
    onSurfaceVariant = KhidmatiTextSecondary,
    outline = KhidmatiOutline,
    error = KhidmatiRed,
    onError = Color.White,
    errorContainer = KhidmatiRedLight,
    onErrorContainer = KhidmatiRed
)

private val DarkColorScheme = darkColorScheme(
    primary = KhidmatiGreenAccent,
    onPrimary = Color.Black,
    primaryContainer = KhidmatiDarkGreen,
    onPrimaryContainer = KhidmatiLightGreen,
    secondary = Color(0xFF6EE7B7),
    onSecondary = Color.Black,
    background = Color(0xFF111814),
    onBackground = Color(0xFFE2E8E4),
    surface = Color(0xFF19231E),
    onSurface = Color(0xFFE2E8E4),
    surfaceVariant = Color(0xFF222F29),
    onSurfaceVariant = Color(0xFF9EABA3),
    outline = Color(0xFF38473F),
    error = Color(0xFFF87171),
    onError = Color.Black
)

@Composable
fun KhidmatiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
