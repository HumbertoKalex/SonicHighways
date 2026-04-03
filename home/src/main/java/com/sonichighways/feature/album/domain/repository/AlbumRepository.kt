package com.sonichighways.feature.album.domain.repository

import com.sonichighways.feature.home.domain.model.Song

internal interface AlbumRepository {
    suspend fun getAlbumSongs(albumId: Long): Result<List<Song>>
}