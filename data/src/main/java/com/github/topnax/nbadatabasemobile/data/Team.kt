package com.github.topnax.nbadatabasemobile.data

/**
 * Represents an NBA team.
 */
data class Team(
    val id: String,
    val conference: String?,
    val division: String?,
    val city: String?,
    val name: String,
    val fullName: String,
    val abbreviation: String
)

data class TeamPreview(
    val id: String,
    val name: String,
    val abbreviation: String
)
