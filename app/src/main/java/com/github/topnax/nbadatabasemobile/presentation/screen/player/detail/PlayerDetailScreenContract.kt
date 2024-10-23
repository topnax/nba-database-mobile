package com.github.topnax.nbadatabasemobile.presentation.screen.player.detail

interface PlayerDetailScreenContract {
    data class State (
        val playerId: String
    )

    sealed interface Event {

    }
}