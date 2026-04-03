package com.sonichighways.feature.home.presentation.ui.view.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sonichighways.feature.album.presentation.ui.AlbumContent
import com.sonichighways.feature.home.presentation.ui.view.component.DragHandleOverlay
import com.sonichighways.feature.home.presentation.ui.view.component.HomePlaceholder
import com.sonichighways.feature.home.presentation.ui.view.search.SearchContent
import com.sonichighways.feature.home.presentation.ui.viewmodel.HomeViewModel
import com.sonichighways.feature.player.presentation.ui.view.PlayerContent
import com.sonichighways.feature.player.presentation.ui.viewmodel.PlayerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeAdaptiveScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val homeViewModel: HomeViewModel = koinViewModel()
    val playerViewModel: PlayerViewModel = koinViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val selectedSong by playerViewModel.selectedSong.collectAsStateWithLifecycle()
    val selectedAlbum by homeViewModel.selectedAlbum.collectAsStateWithLifecycle()
    val isPlaying by playerViewModel.isPlaying.collectAsStateWithLifecycle()
    val currentPosition by playerViewModel.currentPosition.collectAsStateWithLifecycle()

    val adaptiveState = rememberHomeAdaptiveState(hasSelectedSong = selectedSong != null)

    BackHandler(enabled = selectedSong != null && adaptiveState.isTabletMode) {
        playerViewModel.clearSelection()
        if (!adaptiveState.isListVisible) adaptiveState.onToggleList()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                homeViewModel.loadHistory()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .width(adaptiveState.currentWidthDp)
                    .fillMaxHeight()
            ) {
                Crossfade(
                    targetState = selectedAlbum,
                    label = "LeftPanelNavigation"
                ) { currentAlbum ->
                    if (currentAlbum != null) {
                        AlbumContent(
                            albumName = currentAlbum.albumName,
                            artistName = currentAlbum.artistName,
                            albumCoverUrl = currentAlbum.albumCoverUrl,
                            songs = currentAlbum.songs,
                            onBackClick = { homeViewModel.closeAlbum() },
                            onSongClick = { song -> playerViewModel.selectSong(song) }
                        )
                    } else {
                        SearchContent(
                            uiState = uiState,
                            onSearch = { homeViewModel.searchSongs(it) },
                            onSongClick = { song ->
                                playerViewModel.selectSong(song)
                                homeViewModel.onSongSelected(song)
                            },
                            onViewAlbumClick = { song -> homeViewModel.openAlbum(song) },
                            onToggleList = adaptiveState.onToggleList,
                            isListVisible = adaptiveState.isListVisible
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                if (selectedSong != null) {
                    PlayerContent(
                        song = selectedSong!!,
                        isPlaying = isPlaying,
                        currentPosition = currentPosition,
                        onPlayPauseClick = { playerViewModel.togglePlayPause() },
                        onSeek = { progress -> playerViewModel.seekTo(progress) },
                        onNextClick = { /* TODO */ },
                        onPreviousClick = { /* TODO */ }
                    )
                } else {
                    HomePlaceholder(
                        isListVisible = adaptiveState.isListVisible,
                        onToggleList = adaptiveState.onToggleList
                    )
                }
            }
        }

        DragHandleOverlay(state = adaptiveState)
    }
}