package com.sonichighways.feature.album.presentation.ui

import com.sonichighways.feature.home.domain.model.Song

data class AlbumState(
    val albumId: Long,
    val albumName: String,
    val artistName: String,
    val albumCoverUrl: String,
    val songs: List<Song>
)