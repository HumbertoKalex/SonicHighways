package com.sonichighways.core.network.local.di

import androidx.room.Room
import com.sonichighways.core.network.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.jvm.java

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "sonic_highways_db"
        ).build()
    }

    single { get<AppDatabase>().songDao() }
}