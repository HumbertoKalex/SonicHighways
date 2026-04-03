package com.sonichighways.feature.home.presentation.ui.viewmodel

import com.sonichighways.feature.home.domain.model.Song

sealed interface HomeUiState {
    data object Initial : HomeUiState
    data object Loading : HomeUiState
    data class Success(val songs: List<Song>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}