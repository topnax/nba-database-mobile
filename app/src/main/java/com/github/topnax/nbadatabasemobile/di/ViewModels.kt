package com.github.topnax.nbadatabasemobile.di

import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenViewModel
import com.github.topnax.nbadatabasemobile.presentation.screen.player.list.PlayersListViewScreenModel
import com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail.TeamDetailScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel { (playerId: String, playerFullName: String) ->
        PlayerDetailScreenViewModel(playerRepository = get(), playerId = playerId, playerFullName = playerFullName)
    }

    // team view detail model
    viewModel { (teamId: String, teamName: String) ->
        TeamDetailScreenViewModel(teamRepository = get(), teamId = teamId, teamName = teamName)
    }

    viewModel {
        PlayersListViewScreenModel(playerRepository = get())
    }
}
