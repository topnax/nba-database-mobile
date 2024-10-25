package com.github.topnax.nbadatabasemobile.implementation.player

import com.github.topnax.nbadatabasemobile.balldontlieapi.BalldontlieApi
import com.github.topnax.nbadatabasemobile.data.Team
import com.github.topnax.nbadatabasemobile.domain.team.TeamRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BalldontlieTeamRepository(
    api: BalldontlieApi,
    private val dispatcher: CoroutineDispatcher
) : TeamRepository {
    // backing field to prevent direct access to the API
    private val _api = api

    override suspend fun getTeamById(teamId: String): Team {
        return useApi { getTeamById(teamId = teamId.toInt()) }.data.toDataTeam()
    }

    // TODO refactor into reusable function for BalldontlieApi
    private suspend fun <T> useApi(block: suspend BalldontlieApi.() -> T): T =
        withContext(dispatcher) {
            block(_api)
        }

}

private typealias BalldontlieTeam = com.github.topnax.nbadatabasemobile.balldontlieapi.Team

private fun BalldontlieTeam.toDataTeam() =
    Team(
        id = this.id.toString(),
        abbreviation = this.abbreviation,
        city = this.city,
        conference = this.conference,
        division = this.division,
        fullName = this.fullName,
        name = this.name
    )
