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

    fun selectSong(song: Song) {
        _selectedSong.value = song
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

    override fun onCleared() {
        super.onCleared()
        audioPlayerManager.release()
    }
}