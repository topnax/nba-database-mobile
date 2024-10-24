package com.github.topnax.nbadatabasemobile.domain.player

import com.github.topnax.nbadatabasemobile.data.Page
import com.github.topnax.nbadatabasemobile.data.Player

interface PlayerRepository {
    suspend fun getPlayers(
        cursor: Int,
        pageSize: Int
    ): Page<Int, List<Player>>

    suspend fun getPlayerById(
        playerId: String
    ): Player
}