package com.github.topnax.nbadatabasemobile.balldontlieapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val position: String,
    val height: String?,
    val weight: Int?,
    @SerialName("jersey_number") val jerseyNumber: String?,
    val college: String?,
    val country: String?,
    @SerialName("draft_year") val draftYear: Int?,
    @SerialName("draft_round") val draftRound: Int?,
    @SerialName("draft_number") val draftNumber: Int?,
    val team: Team?
)

@Serializable
data class Team(
    val id: Int,
    val conference: String?,
    val division: String?,
    val city: String?,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val abbreviation: String
)

@Serializable
data class TeamResponse(
    val data: Team
)

@Serializable
data class PlayersResponse(
    val data: List<Player>,
    val meta: Meta
)

@Serializable
data class PlayerResponse(
    val data: Player
)

@Serializable
data class Meta(
    @SerialName("next_cursor") val nextCursor: Int?,
    @SerialName("per_page") val perPage: Int,
)
