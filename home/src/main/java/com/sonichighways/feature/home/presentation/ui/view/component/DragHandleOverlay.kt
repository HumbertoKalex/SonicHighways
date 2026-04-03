package com.sonichighways.feature.home.presentation.ui.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.sonichighways.core.ui.theme.dimens
import com.sonichighways.feature.home.presentation.ui.view.home.HomeAdaptiveState

@Composable
internal fun DragHandleOverlay(state: HomeAdaptiveState) {
    if (!state.isTabletMode) return

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(dimens.dragHandleTouchTargetSize)
            .offset(x = state.handleOffsetXDp)
            .draggable(
                state = state.dragState,
                orientation = Orientation.Horizontal,
                onDragStarted = { state.onDragStarted() },
                onDragStopped = { state.onDragStopped() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(dimens.spacingNano)
                .height(dimens.dragHandleTouchTargetSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
        )
    }
}