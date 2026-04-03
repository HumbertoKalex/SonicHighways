package com.sonichighways.feature.player.presentation.ui.view

import androidx.compose.runtime.Composable
import com.sonichighways.feature.home.domain.model.Song
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.sonichighways.core.ui.theme.black
import com.sonichighways.core.ui.theme.dimens
import com.sonichighways.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerContent(
    song: Song,
    isPlaying: Boolean,
    currentPosition: Long,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onSeek: (Float) -> Unit
) {
    val durationMs = 30000f
    var isDragging by remember { mutableStateOf(false) }
    var localSliderPosition by remember { mutableFloatStateOf(0f) }
    val playbackProgress = (currentPosition.toFloat() / durationMs).coerceIn(0f, 1f)
    val sliderValue = if (isDragging) localSliderPosition else playbackProgress

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
            .padding(dimens.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        AsyncImage(
            model = song.imageUrl.replace("100x100", "600x600"),
            contentDescription = stringResource(id =  R.string.player_content_desc_album_art ),
            modifier = Modifier
                .size(dimens.albumCoverPlayerSize)
                .clip(RoundedCornerShape(dimens.radiusLarge))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(dimens.spacingXLarge))

        Text(
            text = song.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        
        Spacer(modifier = Modifier.height(dimens.spacingNano))
        
        Text(
            text = song.artist,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(dimens.spacingXLarge))

        Slider(
            value = sliderValue,
            onValueChange = { newValue ->
                isDragging = true
                localSliderPosition = newValue },
            onValueChangeFinished = {
                onSeek(localSliderPosition)
                isDragging = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.spacingLarge),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(dimens.spacingLarge)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    modifier = Modifier
                        .height(dimens.spacingMicro)
                        .clip(RoundedCornerShape(dimens.spacingMedium)),
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.White.copy(alpha = 0.25f)
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(dimens.spacingMedium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onPreviousClick) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = stringResource(id = R.string.player_content_desc_prev),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(dimens.iconSizeMedium * 1.5f)
                )
            }

            IconButton(
                onClick = onPlayPauseClick,
                modifier = Modifier.size(dimens.iconSizeMedium * 3f)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(dimens.iconSizeMedium * 1.5f)
                )
            }

            IconButton(onClick = onNextClick) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = stringResource(id = R.string.player_content_desc_next),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(dimens.iconSizeMedium * 1.5f)
                )
            }
        }
    }
}

@Preview(
    name = "Player Content - Tocando",
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun PlayerContentPlayingPreview() {
    val mockSong = Song(
        id = 123L,
        collectionId = 456L,
        title = "The Pretender",
        artist = "Foo Fighters",
        album = "Echoes, Silence, Patience & Grace",
        imageUrl = "",
        previewUrl = "",
        durationMs = 30000L
    )

    MaterialTheme {
        PlayerContent(
            song = mockSong,
            isPlaying = true,
            currentPosition = 15000L,
            onPlayPauseClick = {},
            onNextClick = {},
            onPreviousClick = {},
            onSeek = {}
        )
    }
}