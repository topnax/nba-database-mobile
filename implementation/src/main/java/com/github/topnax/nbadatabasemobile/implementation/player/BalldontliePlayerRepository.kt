package com.github.topnax.nbadatabasemobile.implementation.player

import com.github.topnax.nbadatabasemobile.balldontlieapi.BalldontlieApi
import com.github.topnax.nbadatabasemobile.balldontlieapi.Team
import com.github.topnax.nbadatabasemobile.data.Page
import com.github.topnax.nbadatabasemobile.data.Player
import com.github.topnax.nbadatabasemobile.data.TeamPreview
import com.github.topnax.nbadatabasemobile.domain.player.PlayerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BalldontliePlayerRepository(
    api: BalldontlieApi,
    private val dispatcher: CoroutineDispatcher
): PlayerRepository {
    private val _api = api

    override suspend fun getPlayers(cursor: Int, pageSize: Int): Page<Int, List<Player>> {
        val response = useApi { getPlayers(cursor, pageSize) }
        return response.data.map {
            it.toDataPlayer()
        }.let {
            Page(
                data = it,
                nextCursor = response.meta.nextCursor
            )
        }
    }

    override suspend fun getPlayerById(playerId: String): Player {
        return useApi { getPlayerById(playerId = playerId.toInt()) }.data.toDataPlayer()
    }

    private suspend fun <T> useApi(block: suspend BalldontlieApi.() -> T): T =
        withContext(dispatcher) {
            block(_api)
        }
}
private fun com.github.topnax.nbadatabasemobile.balldontlieapi.Player.toDataPlayer() = Player(
    id = this.id.toString(),
    firstName = this.firstName,
    lastName = this.lastName,
    position = this.position,
    height = this.height,
    weight = this.weight,
    jerseyNumber = this.jerseyNumber,
    college = this.college,
    country = this.country,
    draftYear = this.draftYear,
    draftRound = this.draftRound,
    draftNumber = this.draftNumber,
    teamPreview = this.team?.toDataTeamPreview(),
)

private fun Team.toDataTeamPreview(): TeamPreview =
        TeamPreview(
            id = id.toString(),
            name = name,
            abbreviation = abbreviation,
        )

