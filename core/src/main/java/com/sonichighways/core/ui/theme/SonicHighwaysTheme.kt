package com.sonichighways.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorScheme = darkColorScheme(
    background = black,
    surface = spotlightTeal,
    primary = spotlightTeal,
    surfaceVariant = surfaceVariantDark,
    onBackground = textPrimary,
    onSurface = textPrimary,
    onSurfaceVariant = textSecondary
)

@Composable
fun SonicHighwaysTheme(
    content: @Composable () -> Unit
) {
    val defaultDimens = Dimens()

    CompositionLocalProvider(
        LocalDimens provides defaultDimens
    ) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            content = content
        )
    }
}