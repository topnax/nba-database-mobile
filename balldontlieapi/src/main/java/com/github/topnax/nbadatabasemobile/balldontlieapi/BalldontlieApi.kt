package com.github.topnax.nbadatabasemobile.balldontlieapi

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BalldontlieApi {

    @GET("players")
    suspend fun getPlayers(
        @Query("cursor") page: Int,
        @Query("per_page") perPage: Int
    ): PlayersResponse

    @GET("players/{id}")
    suspend fun getPlayerById(
        @Path("id") playerId: Int
    ): PlayerResponse

    @GET("teams/{id}")
    suspend fun getTeamById(
        @Path("id") teamId: Int
    ): TeamResponse
}
