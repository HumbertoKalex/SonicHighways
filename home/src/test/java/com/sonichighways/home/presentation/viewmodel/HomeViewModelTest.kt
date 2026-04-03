package com.sonichighways.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sonichighways.core.testing.TestCoroutineRule
import com.sonichighways.feature.album.domain.repository.AlbumRepository
import com.sonichighways.feature.home.domain.repository.SearchRepository
import com.sonichighways.feature.home.mock.SongMock
import com.sonichighways.feature.home.presentation.ui.viewmodel.HomeUiState
import com.sonichighways.feature.home.presentation.ui.viewmodel.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var searchRepository: SearchRepository

    @MockK
    private lateinit var albumRepository: AlbumRepository

    private val mockSong = SongMock.singleSong
    private val mockSongsList = listOf(mockSong)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { searchRepository.getHistorySnapshot() } returns emptyList()
        coEvery { searchRepository.searchSongs(any(), any(), any()) } returns Result.success(mockSongsList)
    }

    @Test
    fun `When initialize with empty cache, should fetch default rock songs`() = runTest {
        coEvery { searchRepository.getHistorySnapshot() } returns emptyList()

        val viewModel = HomeViewModel(searchRepository, albumRepository)
        advanceUntilIdle()

        coVerify(exactly = 1) { searchRepository.getHistorySnapshot() }
        coVerify(exactly = 1) { searchRepository.searchSongs("rock", 0, 50) }

        assertEquals(HomeUiState.Success(mockSongsList), viewModel.uiState.value)
    }

    @Test
    fun `When initialize with data in cache, should NOT fetch rock songs`() = runTest {
        val cacheList = listOf(mockSong.copy(title = "Cached Song"))
        coEvery { searchRepository.getHistorySnapshot() } returns cacheList

        val viewModel = HomeViewModel(searchRepository, albumRepository)
        advanceUntilIdle()

        coVerify(exactly = 1) { searchRepository.getHistorySnapshot() }
        coVerify(exactly = 0) { searchRepository.searchSongs("rock", any(), any()) }

        assertEquals(HomeUiState.Success(cacheList), viewModel.uiState.value)
    }

    @Test
    fun `When search songs, should update uiState with results`() = runTest {
        val query = "Foo Fighters"

        coEvery { searchRepository.getHistorySnapshot() } returns emptyList()
        coEvery { searchRepository.searchSongs("rock", 0, 50) } returns Result.success(mockSongsList)
        coEvery { searchRepository.searchSongs(query, 0, 50) } returns Result.success(mockSongsList)

        val viewModel = HomeViewModel(searchRepository, albumRepository)

        viewModel.searchSongs(query)
        advanceUntilIdle()

        coVerify {
            searchRepository.getHistorySnapshot()
            searchRepository.searchSongs("rock", 0, 50)
            searchRepository.searchSongs(query, 0, 50)
        }

        assertEquals(HomeUiState.Success(mockSongsList), viewModel.uiState.value)
    }

    @Test
    fun `When onSongSelected is called, should save song to history`() = runTest {
        coJustRun { searchRepository.saveToHistory(any()) }

        val viewModel = HomeViewModel(searchRepository, albumRepository)

        viewModel.onSongSelected(mockSong)
        advanceUntilIdle()

        coVerify {
            searchRepository.getHistorySnapshot()
            searchRepository.searchSongs("rock", 0, 50)
            searchRepository.saveToHistory(mockSong)
        }
    }

    @Test
    fun `When openAlbum is successful, should filter tracks and update selectedAlbum`() = runTest {
        val validSong = mockSong.copy(previewUrl = "http://preview.com")
        val invalidSong = validSong.copy(id = 2L, previewUrl = "")
        val albumSongs = listOf(validSong, invalidSong)

        coEvery { searchRepository.getHistorySnapshot() } returns emptyList()
        coEvery { searchRepository.searchSongs("rock", 0, 50) } returns Result.success(listOf(validSong))
        coEvery { albumRepository.getAlbumSongs(any()) } returns Result.success(albumSongs)

        val viewModel = HomeViewModel(searchRepository, albumRepository)
        viewModel.openAlbum(validSong)
        advanceUntilIdle()

        coVerify {
            searchRepository.getHistorySnapshot()
            searchRepository.searchSongs("rock", 0, 50)

            albumRepository.getAlbumSongs(validSong.collectionId)
        }

        assertEquals(1, viewModel.selectedAlbum.value?.songs?.size)
    }

    @After
    fun tearDown() {
        confirmVerified(searchRepository, albumRepository)
    }
}