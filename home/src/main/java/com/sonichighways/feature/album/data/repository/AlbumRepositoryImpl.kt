package com.sonichighways.feature.album.data.repository

import com.sonichighways.feature.album.data.remote.AlbumApiService
import com.sonichighways.feature.album.domain.repository.AlbumRepository
import com.sonichighways.feature.home.domain.model.Song

internal class AlbumRepositoryImpl(
    private val albumApiService: AlbumApiService
) : AlbumRepository {

    override suspend fun getAlbumSongs(albumId: Long): Result<List<Song>> {
        return runCatching {
            val response = albumApiService.getAlbumSongs(albumId)

            if (response.isSuccessful) {
                val body = response.body()
                body?.results
                    ?.filter { it.wrapperType == "track" }
                    ?.map { dto ->
                        Song(
                            id = dto.trackId ?: 0L,
                            collectionId = dto.collectionId ?: 0L,
                            title = dto.trackName ?: "Título Desconhecido",
                            artist = dto.artistName ?: "Artista Desconhecido",
                            album = dto.albumName ?: "Álbum Desconhecido",
                            imageUrl = dto.artworkUrl ?: "",
                            previewUrl = dto.previewUrl ?: "",
                            durationMs = dto.durationMs ?: 0L
                        )
                    } ?: emptyList()
            } else {
                throw Exception("Erro HTTP: ${response.code()}")
            }
        }
    }
}