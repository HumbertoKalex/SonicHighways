package com.sonichighways.feature.home.data.remote

import com.google.gson.annotations.SerializedName

data class SearchResponseDTO(
    @SerializedName("resultCount") val resultCount: Int,
    @SerializedName("results") val results: List<SongDTO>
)

data class SongDTO(
    @SerializedName("trackId") val trackId: Long,
    @SerializedName("wrapperType") val wrapperType: String?,
    @SerializedName("collectionId") val collectionId: Long?,
    @SerializedName("trackName") val trackName: String?,
    @SerializedName("artistName") val artistName: String?,
    @SerializedName("collectionName") val albumName: String?,
    @SerializedName("artworkUrl100") val artworkUrl: String?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("trackTimeMillis") val durationMs: Long?
)