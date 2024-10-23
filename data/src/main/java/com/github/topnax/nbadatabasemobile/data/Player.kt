package com.github.topnax.nbadatabasemobile.data

/**
 * Represents an NBA player.
 */
data class Player(
    val id: String,
    val firstName: String,
    val lastName: String,
    val position: String,
    val height: String,
    val weight: Int,
    val jerseyNumber: String,
    val college: String?,
    val country: String,
    val draftYear: Int,
    val draftRound: Int,
    val draftNumber: Int,
    val teamId: String,
    val teamFullName: String,
    val teamAbbreviation: String
)

/**
 * Represents a preview of an NBA player.
 */
data class PlayerPreview(
    val id: Int,
    val name: String,
    val position: String,
    val jerseyNumber: String,
    val teamName: String,
    val teamId: Int
)
