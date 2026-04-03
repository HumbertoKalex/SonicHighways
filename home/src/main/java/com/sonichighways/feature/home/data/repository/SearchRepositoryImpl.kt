package com.sonichighways.feature.home.data.repository

import com.sonichighways.core.network.local.model.SongDao
import com.sonichighways.feature.home.data.mapper.toDomain
import com.sonichighways.feature.home.data.mapper.toEntity
import com.sonichighways.feature.home.data.remote.SearchApiService
import com.sonichighways.feature.home.domain.model.Song
import com.sonichighways.feature.home.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

internal class SearchRepositoryImpl(
    private val apiService: SearchApiService,
    private val songDao: SongDao
) : SearchRepository {

    override suspend fun searchSongs(query: String, offset: Int, limit: Int): Result<List<Song>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchSongs(term = query, offset = offset, limit = limit)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val songs = body.results.map { it.toDomain() }
                        Result.success(songs)
                    } else {
                        Result.failure(Exception("Empty response body"))
                    }
                } else {
                    Result.failure(Exception("API Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun saveToHistory(song: Song) {
        songDao.saveSong(song.toEntity())
    }

    override suspend fun getHistorySnapshot(): List<Song> {
        return songDao.getRecentSongs().first().map { it.toDomain() }
    }
}