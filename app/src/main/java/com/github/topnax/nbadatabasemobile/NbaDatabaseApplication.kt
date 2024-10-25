package com.github.topnax.nbadatabasemobile

import android.app.Application
import com.github.topnax.nbadatabasemobile.di.networkModule
import com.github.topnax.nbadatabasemobile.di.repositoryModule
import com.github.topnax.nbadatabasemobile.di.viewmodelModule
import org.koin.core.context.startKoin
import timber.log.Timber

class NbaDatabaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // plant timber debug tree if in debug
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            modules(
                networkModule, repositoryModule, viewmodelModule
            )
        }
    }
}