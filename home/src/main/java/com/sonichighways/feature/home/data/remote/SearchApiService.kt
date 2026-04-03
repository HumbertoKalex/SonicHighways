package com.sonichighways.feature.home.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchApiService {

    @GET("search")
    suspend fun searchSongs(
        @Query("term") term: String,
        @Query("media") media: String = "music",
        @Query("entity") entity: String = "song",
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0
    ): Response<SearchResponseDTO>
}