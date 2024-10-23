package com.github.topnax.nbadatabasemobile

import android.app.Application
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class NbaDatabaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                module {
                    viewModel { (playerId: String) ->
                        PlayerDetailScreenViewModel(playerId)
                    }
                }
            )

        }
    }
}