package com.github.topnax.nbadatabasemobile.domain.team

import com.github.topnax.nbadatabasemobile.data.Team

interface TeamRepository {
    suspend fun getTeams(
        page: Int,
        pageSize: Int
    ): List<Team>

    suspend fun getTeamById(
        teamId: String
    ): Team
}