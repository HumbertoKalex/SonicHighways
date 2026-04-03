package com.sonichighways.feature.home.domain.model

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val collectionId: Long,
    val imageUrl: String,
    val previewUrl: String,
    val durationMs: Long
)