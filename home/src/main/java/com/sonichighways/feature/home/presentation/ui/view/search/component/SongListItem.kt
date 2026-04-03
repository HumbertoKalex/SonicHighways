package com.sonichighways.feature.home.presentation.ui.view.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.sonichighways.core.ui.theme.dimens
import com.sonichighways.core.ui.theme.surfaceVariantDark
import com.sonichighways.feature.home.domain.model.Song

@Composable
internal fun SongListItem(
    song: Song, onClick: () -> Unit, onViewAlbumClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.radiusMedium))
            .clickable { onClick() }
            .padding(dimens.spacingMicro),
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = song.imageUrl,
            contentDescription = "Album Art",
            modifier = Modifier
                .size(dimens.albumCoverListSize)
                .clip(RoundedCornerShape(dimens.radiusSmall))
                .background(Color.DarkGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(dimens.spacingMedium))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = song.artist,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }

        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More Options", tint = Color.Gray)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(surfaceVariantDark)
            ) {
                DropdownMenuItem(text = { Text("View album", color = Color.White) }, onClick = {
                    expanded = false
                    onViewAlbumClick()
                })
            }
        }
    }
}

@Preview(
    name = "Song List Item",
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun SongListItemPreview() {
    val mockSong = Song(
        id = 1L,
        collectionId = 100L,
        title = "Welcome to the Black Parade",
        artist = "My Chemical Romance",
        album = "The Black Parade",
        imageUrl = "",
        previewUrl = "",
        durationMs = 311000L
    )

    MaterialTheme {
        SongListItem(
            song = mockSong,
            onClick = {},
            onViewAlbumClick = {}
        )
    }
}