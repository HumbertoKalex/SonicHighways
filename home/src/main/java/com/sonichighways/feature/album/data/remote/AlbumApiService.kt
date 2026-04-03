package com.sonichighways.feature.album.data.remote

import com.sonichighways.feature.home.data.remote.SearchResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface AlbumApiService {
    @GET("lookup")
    suspend fun getAlbumSongs(
        @Query("id") albumId: Long,
        @Query("entity") entity: String = "song"
    ): Response<SearchResponseDTO>
}