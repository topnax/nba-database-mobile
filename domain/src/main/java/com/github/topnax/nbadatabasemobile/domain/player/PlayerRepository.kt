package com.github.topnax.nbadatabasemobile.domain.player

import com.github.topnax.nbadatabasemobile.data.Player

interface PlayerRepository {
    suspend fun getPlayers(
        page: Int,
        pageSize: Int
    ): List<Player>

    suspend fun getPlayerById(
        playerId: String
    ): Player
}