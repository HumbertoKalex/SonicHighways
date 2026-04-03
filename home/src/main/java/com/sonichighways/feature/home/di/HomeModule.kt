package com.sonichighways.feature.home.di

import com.sonichighways.feature.home.data.remote.SearchApiService
import com.sonichighways.feature.home.data.repository.SearchRepositoryImpl
import com.sonichighways.feature.home.domain.repository.SearchRepository
import com.sonichighways.core.player.AudioPlayerManager
import com.sonichighways.feature.album.data.remote.AlbumApiService
import com.sonichighways.feature.album.data.repository.AlbumRepositoryImpl
import com.sonichighways.feature.album.domain.repository.AlbumRepository
import com.sonichighways.feature.home.presentation.ui.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    single<SearchApiService> {
        get<Retrofit>().create(SearchApiService::class.java)
    }
    single<AlbumApiService> {
        get<Retrofit>().create(AlbumApiService::class.java)
    }

    single<AlbumRepository> { AlbumRepositoryImpl(albumApiService = get()) }
    single<SearchRepository> { SearchRepositoryImpl(apiService = get(), songDao = get()) }

    single { AudioPlayerManager(androidContext()) }

    viewModel { HomeViewModel(repository = get(), albumRepository = get()) }
}