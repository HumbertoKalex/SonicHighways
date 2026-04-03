package com.sonichighways.feature.home.presentation.ui.view.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

import com.sonichighways.core.ui.theme.dimens

const val FALLBACK_SCREEN_WIDTH_PX = 1080f
@Stable
internal class HomeAdaptiveState(
    val isTabletMode: Boolean,
    val isListVisible: Boolean,
    val currentWidthDp: Dp,
    val handleOffsetXDp: Dp,
    val dragState: DraggableState,
    val onToggleList: () -> Unit,
    val onDragStarted: () -> Unit,
    val onDragStopped: () -> Unit
)

@Composable
internal fun rememberHomeAdaptiveState(
    hasSelectedSong: Boolean
): HomeAdaptiveState {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val containerWidthPx = windowInfo.containerSize.width.toFloat()
    val screenWidthPx = if (containerWidthPx > 0f) containerWidthPx else FALLBACK_SCREEN_WIDTH_PX
    val screenWidthDp = with(density) { screenWidthPx.toDp() }
    val isTabletMode = screenWidthDp > dimens.tabletBreakpoint
    val minListWidthPx = 0f
    val defaultSplitWidthPx = with(density) { dimens.defaultSplitWidth.toPx() }
    val dragSnapEdgeThresholdPx = with(density) { dimens.dragSnapEdgeThreshold.toPx() }
    val dragWakeUpThresholdPx = with(density) { dimens.dragWakeUpThreshold.toPx() }
    var isListVisible by remember { mutableStateOf(true) }
    var isDragging by remember { mutableStateOf(false) }
    var currentWidthPx by remember(screenWidthPx) {
        mutableFloatStateOf(if (!hasSelectedSong) screenWidthPx else defaultSplitWidthPx)
    }

    var lastDraggedWidthPx by remember(screenWidthPx) {
        mutableFloatStateOf(defaultSplitWidthPx.coerceIn(minListWidthPx, screenWidthPx))
    }

    val targetWidthPx = remember(hasSelectedSong, isListVisible, lastDraggedWidthPx, screenWidthPx) {
        when {
            !isListVisible -> minListWidthPx
            !hasSelectedSong -> screenWidthPx
            else -> lastDraggedWidthPx
        }
    }

    val animatableWidth = remember { Animatable(currentWidthPx) }
    LaunchedEffect(targetWidthPx, isDragging) {
        if (!isDragging) {
            animatableWidth.snapTo(currentWidthPx)
            animatableWidth.animateTo(
                targetValue = targetWidthPx,
                animationSpec = spring(stiffness = Spring.StiffnessLow)
            ) {
                currentWidthPx = value
            }
        }
    }

    LaunchedEffect(hasSelectedSong, screenWidthPx) {
        if (hasSelectedSong) {
            isListVisible = true
            if (lastDraggedWidthPx >= screenWidthPx - with(density) { dragSnapEdgeThresholdPx }) {
                lastDraggedWidthPx = defaultSplitWidthPx
            }
        }
    }

    val dragState = rememberDraggableState { delta ->
        isDragging = true
        val newWidth = (currentWidthPx + delta).coerceIn(minListWidthPx, screenWidthPx)
        currentWidthPx = newWidth
        if (!isListVisible && newWidth > with(density) { dragWakeUpThresholdPx }) {
            isListVisible = true
        }
        lastDraggedWidthPx = newWidth
    }

    val handleOffsetXDp = with(density) {
        (currentWidthPx - dimens.spacingLarge.toPx()).coerceIn(0f, screenWidthPx - dimens.dragHandleTouchTargetSize.toPx()).toDp()
    }

    return remember(
        isTabletMode, isListVisible, currentWidthPx, handleOffsetXDp, dragState
    ) {
        HomeAdaptiveState(
            isTabletMode = isTabletMode,
            isListVisible = isListVisible,
            currentWidthDp = with(density) { currentWidthPx.toDp() },
            handleOffsetXDp = handleOffsetXDp,
            dragState = dragState,
            onToggleList = { isListVisible = !isListVisible },
            onDragStarted = { isDragging = true },
            onDragStopped = {
                isDragging = false
                if (currentWidthPx <= dragSnapEdgeThresholdPx) {
                    isListVisible = false
                    lastDraggedWidthPx = defaultSplitWidthPx
                } else if (currentWidthPx >= screenWidthPx - dragSnapEdgeThresholdPx) {
                    lastDraggedWidthPx = screenWidthPx
                }
            }
        )
    }
}