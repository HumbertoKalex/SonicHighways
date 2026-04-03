package com.sonichighways.core.network.remote.model

import com.google.gson.annotations.SerializedName

internal data class SongRemoteModel(
    @SerializedName("trackId") val trackId: Long,
    @SerializedName("trackName") val trackName: String?,
    @SerializedName("artistName") val artistName: String?,
    @SerializedName("collectionName") val albumName: String?,
    @SerializedName("artworkUrl100") val artworkUrl: String?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("trackTimeMillis") val durationMs: Long?
)