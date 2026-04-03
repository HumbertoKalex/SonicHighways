package com.sonichighways.app

import android.app.Application
import com.sonichighways.core.network.local.di.databaseModule
import com.sonichighways.core.network.remote.di.networkModule
import com.sonichighways.feature.home.di.homeModule
import com.sonichighways.feature.player.di.playerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SonicHighwaysApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SonicHighwaysApp)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    playerModule,
                    homeModule
                )
            )
        }
    }
}