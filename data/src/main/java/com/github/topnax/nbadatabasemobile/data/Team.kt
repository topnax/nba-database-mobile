package com.github.topnax.nbadatabasemobile.data

data class Team(
    val id: String,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    val fullName: String,
    val abbreviation: String
)

data class TeamPreview(
    val id: String,
    val city: String,
    val name: String,
    val abbreviation: String
)
