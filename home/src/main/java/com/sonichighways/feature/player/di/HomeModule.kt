package com.sonichighways.feature.player.di

import com.sonichighways.core.player.AudioPlayerManager
import com.sonichighways.feature.player.presentation.ui.viewmodel.PlayerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val playerModule = module {
    single { AudioPlayerManager(androidContext()) }
    viewModel { PlayerViewModel(audioPlayerManager = get()) }
}