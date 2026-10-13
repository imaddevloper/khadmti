package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ModernLightColorScheme = lightColorScheme(
    primary = KhidmatiPrimary,
    onPrimary = Color.White,
    primaryContainer = KhidmatiPrimarySurface,
    onPrimaryContainer = KhidmatiPrimaryHover,
    secondary = KhidmatiSecondary,
    onSecondary = Color.White,
    secondaryContainer = KhidmatiSecondarySurface,
    onSecondaryContainer = KhidmatiSecondary,
    tertiary = KhidmatiAmber,
    onTertiary = Color.White,
    tertiaryContainer = KhidmatiAmberLight,
    onTertiaryContainer = KhidmatiAmber,
    background = KhidmatiBackground,
    onBackground = KhidmatiTextPrimary,
    surface = KhidmatiSurface,
    onSurface = KhidmatiTextPrimary,
    surfaceVariant = KhidmatiSurfaceVariant,
    onSurfaceVariant = KhidmatiTextMuted,
    outline = KhidmatiBorder,
    error = KhidmatiRed,
    onError = Color.White,
    errorContainer = KhidmatiRedLight,
    onErrorContainer = KhidmatiRed
)

@Composable
fun KhidmatiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Prioritize clean, bright, modern light aesthetic
    MaterialTheme(
        colorScheme = ModernLightColorScheme,
        typography = Typography,
        content = content
    )
}
