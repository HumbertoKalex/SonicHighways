package com.sonichighways.feature.home.domain.repository

import com.sonichighways.feature.home.domain.model.Song
import kotlinx.coroutines.flow.Flow

internal interface SearchRepository {
    suspend fun searchSongs(query: String, offset: Int, limit: Int): Result<List<Song>>
    suspend fun saveToHistory(song: Song)
    suspend fun getHistorySnapshot(): List<Song>
}