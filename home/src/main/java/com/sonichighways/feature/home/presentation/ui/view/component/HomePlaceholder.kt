package com.sonichighways.feature.home.presentation.ui.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.sonichighways.core.R
import com.sonichighways.core.ui.theme.black
import com.sonichighways.core.ui.theme.dimens
import com.sonichighways.core.ui.theme.spotlightTeal

@Composable
internal fun HomePlaceholder(
    isListVisible: Boolean,
    onToggleList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(spotlightTeal, black)
                )
            )
    ) {
        if (!isListVisible) {
            IconButton(
                onClick = onToggleList,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(dimens.spacingMedium)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuOpen,
                    contentDescription = "Expand list",
                    tint = Color.White
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Sonic Highways Logo",
            modifier = Modifier
                .size(dimens.splashLogoSize)
                .align(Alignment.Center)
        )
    }
}