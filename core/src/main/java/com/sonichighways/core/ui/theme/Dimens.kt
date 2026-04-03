package com.sonichighways.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val spacingNano: Dp = 4.dp,
    val spacingMicro: Dp = 8.dp,
    val spacingSmall: Dp = 12.dp,
    val spacingMedium: Dp = 16.dp,
    val spacingLarge: Dp = 24.dp,
    val spacingXLarge: Dp = 32.dp,
    val spacingXXLarge: Dp = 48.dp,
    val radiusSmall: Dp = 4.dp,
    val radiusMedium: Dp = 8.dp,
    val radiusLarge: Dp = 12.dp,
    val radiusPill: Dp = 50.dp,
    val iconSizeMedium: Dp = 24.dp,
    val albumCoverListSize: Dp = 56.dp,
    val albumCoverPlayerSize: Dp = 300.dp,
    val splashLogoSize: Dp = 150.dp,
    val tabletBreakpoint: Dp = 600.dp,
    val defaultSplitWidth: Dp = 350.dp,
    val dragHandleTouchTargetSize: Dp = 48.dp ,
    val dragSnapEdgeThreshold: Dp = 50.dp,
    val dragWakeUpThreshold: Dp = 10.dp,
    val dragVisualHandleFadeThreshold: Dp = 30.dp,
    val placeholderLogoSize: Dp = 150.dp,
    val placeholderMenuPadding: Dp = 16.dp,
)

val LocalDimens = compositionLocalOf { Dimens() }

val dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current