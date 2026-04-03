package com.sonichighways.feature.home.data.mapper

import com.sonichighways.core.network.local.model.SongEntity
import com.sonichighways.feature.home.data.remote.SongDTO
import com.sonichighways.feature.home.domain.model.Song

fun SongDTO.toDomain(): Song {
    return Song(
        id = trackId,
        title = trackName ?: "Unknown Title",
        artist = artistName ?: "Unknown Artist",
        album = albumName ?: "Unknown Album",
        collectionId = collectionId ?: 0L,
        imageUrl = artworkUrl?.replace("100x100bb", "600x600bb") ?: "",
        previewUrl = previewUrl ?: "",
        durationMs = durationMs ?: 0L
    )
}

fun SongEntity.toDomain() = Song(
    id = id,
    collectionId = collectionId,
    title = title,
    artist = artist,
    album = album,
    imageUrl = imageUrl,
    previewUrl = previewUrl,
    durationMs = durationMs
)

fun Song.toEntity() = SongEntity(
    id = id,
    collectionId = collectionId,
    title = title,
    artist = artist,
    album = album,
    imageUrl = imageUrl,
    previewUrl = previewUrl,
    durationMs = durationMs
)