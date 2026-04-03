package com.sonichighways.core.network.remote.model

import com.google.gson.annotations.SerializedName

internal data class TunesResponse(
    @SerializedName("resultCount") val resultCount: Int,
    @SerializedName("results") val results: List<SongRemoteModel>
)