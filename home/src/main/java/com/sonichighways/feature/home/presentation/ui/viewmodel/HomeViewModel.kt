package com.sonichighways.feature.home.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonichighways.feature.home.domain.model.Song
import com.sonichighways.feature.home.domain.repository.SearchRepository
import com.sonichighways.core.player.AudioPlayerManager
import com.sonichighways.feature.album.domain.repository.AlbumRepository
import com.sonichighways.feature.album.presentation.ui.AlbumState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val repository: SearchRepository,
    private val albumRepository: AlbumRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private val _selectedAlbum = MutableStateFlow<AlbumState?>(null)
    val selectedAlbum = _selectedAlbum.asStateFlow()

    init {
        loadHistory()
    }

    fun searchSongs(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            val result = repository.searchSongs(query = query, offset = 0, limit = 50)

            result.fold(
                onSuccess = { songs ->
                    if (songs.isEmpty()) {
                        _uiState.value = HomeUiState.Error("Nenhuma música encontrada.")
                    } else {
                        _uiState.value = HomeUiState.Success(songs)
                    }
                },
                onFailure = { error ->
                    _uiState.value = HomeUiState.Error(error.message ?: "Erro desconhecido")
                }
            )
        }
    }

    fun openAlbum(song: Song) {
        viewModelScope.launch {
            val result = albumRepository.getAlbumSongs(song.collectionId)

            result.onSuccess { allSongs ->
                val trackList = allSongs.filter { it.previewUrl.isNotEmpty() }

                _selectedAlbum.value = AlbumState(
                    albumId = song.collectionId,
                    albumName = song.album,
                    artistName = song.artist,
                    albumCoverUrl = song.imageUrl,
                    songs = trackList
                )
            }.onFailure { exception ->
                // TODO: logar o erro.
                // _uiError.value = exception.message
            }
        }
    }

    fun closeAlbum() {
        _selectedAlbum.value = null
    }

    fun loadHistory() {
        viewModelScope.launch {
            val history = repository.getHistorySnapshot()
            if (history.isNotEmpty()) {
                _uiState.value = HomeUiState.Success(history)
            } else {
                searchSongs("rock")
            }
        }
    }

    fun onSongSelected(song: Song) {
        viewModelScope.launch {
            repository.saveToHistory(song)
        }
    }

}