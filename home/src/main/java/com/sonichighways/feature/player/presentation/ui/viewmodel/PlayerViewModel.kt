package com.sonichighways.feature.player.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sonichighways.feature.home.domain.model.Song
import com.sonichighways.core.player.AudioPlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PlayerViewModel(
    private val audioPlayerManager: AudioPlayerManager
) : ViewModel() {

    private val _selectedSong = MutableStateFlow<Song?>(null)
    val selectedSong: StateFlow<Song?> = _selectedSong.asStateFlow()
    val isPlaying = audioPlayerManager.isPlaying
    val currentPosition = audioPlayerManager.currentPosition
    private val _playlist = MutableStateFlow<List<Song>>(emptyList())

    fun selectSong(song: Song, playlist: List<Song>?) {
        _selectedSong.value = song
        _playlist.value = playlist ?: emptyList()
        song.previewUrl.let { url ->
            audioPlayerManager.playSong(url)
        }
    }

    fun togglePlayPause() {
        if (isPlaying.value) {
            audioPlayerManager.pause()
        } else {
            audioPlayerManager.resume()
        }
    }

    fun seekTo(progress: Float) {
        val durationMs = 30000L // 30 segundos
        val newPosition = (progress * durationMs).toLong()
        audioPlayerManager.seekTo(newPosition)
    }

    fun clearSelection() {
        _selectedSong.value = null
        audioPlayerManager.pause()
    }

    fun skipNext() {
        val currentList = _playlist.value
        val currentIndex = currentList.indexOf(_selectedSong.value)

        if (currentIndex != -1 && currentList.isNotEmpty()) {
            val nextIndex = (currentIndex + 1) % currentList.size
            selectSong(currentList[nextIndex], currentList)
        }
    }

    fun skipPrevious() {
        val currentList = _playlist.value
        val currentIndex = currentList.indexOf(_selectedSong.value)

        if (currentIndex != -1 && currentList.isNotEmpty()) {
            val prevIndex = (currentIndex - 1 + currentList.size) % currentList.size
            selectSong(currentList[prevIndex], currentList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayerManager.release()
    }
}