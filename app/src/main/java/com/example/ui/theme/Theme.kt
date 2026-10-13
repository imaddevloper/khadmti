package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
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

private val ModernDarkColorScheme = darkColorScheme(
    primary = KhidmatiPrimaryLight,
    onPrimary = Color(0xFF022C22),
    primaryContainer = KhidmatiDarkPrimarySurface,
    onPrimaryContainer = Color(0xFFA7F3D0),
    secondary = KhidmatiSecondaryLight,
    onSecondary = Color(0xFF082F49),
    secondaryContainer = Color(0xFF0C4A6E),
    onSecondaryContainer = Color(0xFFBAE6FD),
    tertiary = KhidmatiAmber,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF78350F),
    onTertiaryContainer = Color(0xFFFEF3C7),
    background = KhidmatiDarkBackground,
    onBackground = KhidmatiDarkTextPrimary,
    surface = KhidmatiDarkSurface,
    onSurface = KhidmatiDarkTextPrimary,
    surfaceVariant = KhidmatiDarkSurfaceVariant,
    onSurfaceVariant = KhidmatiDarkTextSecondary,
    outline = KhidmatiDarkBorder,
    error = Color(0xFFF87171),
    onError = Color.Black,
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFECACA)
)

@Composable
fun KhidmatiTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ModernDarkColorScheme else ModernLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
