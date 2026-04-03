package com.sonichighways.player.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sonichighways.core.player.AudioPlayerManager
import com.sonichighways.core.testing.TestCoroutineRule
import com.sonichighways.feature.home.mock.SongMock
import com.sonichighways.feature.player.presentation.ui.viewmodel.PlayerViewModel
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PlayerViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var audioPlayerManager: AudioPlayerManager

    private lateinit var viewModel: PlayerViewModel

    private val isPlayingFlow = MutableStateFlow(false)
    private val currentPositionFlow = MutableStateFlow(0L)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { audioPlayerManager.isPlaying } returns isPlayingFlow
        every { audioPlayerManager.currentPosition } returns currentPositionFlow

        viewModel = PlayerViewModel(audioPlayerManager)
    }

    @Test
    fun `When selectSong is called, should update selectedSong and start playback`() = runTest {
        val song = SongMock.singleSong
        
        justRun { audioPlayerManager.playSong(any()) }

        viewModel.selectSong(song)

        assertEquals(song, viewModel.selectedSong.value)
        verify{
            audioPlayerManager.isPlaying
            audioPlayerManager.currentPosition
            audioPlayerManager.playSong(song.previewUrl)
        }
    }

    @Test
    fun `When togglePlayPause is called while playing, should call pause`() = runTest {
        isPlayingFlow.value = true
        justRun { audioPlayerManager.pause() }

        viewModel.togglePlayPause()

        verify {
            audioPlayerManager.isPlaying
            audioPlayerManager.currentPosition
            audioPlayerManager.pause()
        }
    }

    @Test
    fun `When togglePlayPause is called while paused, should call resume`() = runTest {
        isPlayingFlow.value = false
        justRun { audioPlayerManager.resume() }

        viewModel.togglePlayPause()

        verify{
            audioPlayerManager.isPlaying
            audioPlayerManager.currentPosition
            audioPlayerManager.resume()
        }
    }

    @Test
    fun `When seekTo is called, should calculate correct position based on 30s duration`() = runTest {
        val progress = 0.5f
        val expectedPosition = 15000L
        
        justRun { audioPlayerManager.seekTo(any()) }

        viewModel.seekTo(progress)

        verify{
            audioPlayerManager.isPlaying
            audioPlayerManager.currentPosition
            audioPlayerManager.seekTo(expectedPosition)
        }
    }

    @Test
    fun `When clearSelection is called, should reset song and pause playback`() = runTest {
        justRun { audioPlayerManager.pause() }
        
        viewModel.clearSelection()

        assertNull(viewModel.selectedSong.value)
        verify{
            audioPlayerManager.isPlaying
            audioPlayerManager.currentPosition
            audioPlayerManager.pause()
        }
    }

    @After
    fun tearDown() {
        confirmVerified(audioPlayerManager)
    }
}