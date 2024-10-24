package com.github.topnax.nbadatabasemobile.di

import com.github.topnax.nbadatabasemobile.domain.player.PlayerRepository
import com.github.topnax.nbadatabasemobile.domain.team.TeamRepository
import com.github.topnax.nbadatabasemobile.implementation.player.BalldontliePlayerRepository
import com.github.topnax.nbadatabasemobile.implementation.player.BalldontlieTeamRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
     single<PlayerRepository> { BalldontliePlayerRepository(
         api = get(),
         dispatcher = Dispatchers.IO
     ) }

    single<TeamRepository> { BalldontlieTeamRepository(
        api = get(),
        dispatcher = Dispatchers.IO
    ) }
}