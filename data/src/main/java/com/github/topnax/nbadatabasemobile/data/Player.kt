package com.github.topnax.nbadatabasemobile.data

/**
 * Represents an NBA player.
 */
data class Player(
    val id: String,
    val firstName: String,
    val lastName: String,
    val position: String,
    val height: String?,
    val weight: Int?,
    val jerseyNumber: String?,
    val college: String?,
    val country: String?,
    val draftYear: Int?,
    val draftRound: Int?,
    val draftNumber: Int?,
    val teamPreview: TeamPreview?
) {
    val fullName = "$firstName $lastName"
}
