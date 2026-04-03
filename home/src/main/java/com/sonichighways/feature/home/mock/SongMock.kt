package com.sonichighways.feature.home.mock

import com.sonichighways.feature.home.domain.model.Song

object SongMock {
    val singleSong = Song(
        id = 1L,
        collectionId = 100L,
        title = "Welcome to the Black Parade",
        artist = "My Chemical Romance",
        album = "The Black Parade",
        imageUrl = "",
        previewUrl = "https://audio-ssl.itunes.apple.com/apple-assets-us-std-000001/...",
        durationMs = 311000L
    )

    val singleSongAlternative = Song(
        id = 2L,
        collectionId = 101L,
        title = "The Pretender",
        artist = "Foo Fighters",
        album = "Echoes, Silence, Patience & Grace",
        imageUrl = "",
        previewUrl = "https://audio-ssl.itunes.apple.com/apple-assets-us-std-000001/...",
        durationMs = 269000L
    )

    val songList = listOf(
        Song(1L, "Helena", "My Chemical Romance", "Three Cheers for Sweet Revenge", 100L, "","", 203000L),
        Song(2L, "I'm Not Okay (I Promise)", "My Chemical Romance", "Three Cheers for Sweet Revenge", 0L, "", "",186000L),
        Song(3L,"Teenagers", "My Chemical Romance", "The Black Parade", 100L, "", "",161000L)
    )
}