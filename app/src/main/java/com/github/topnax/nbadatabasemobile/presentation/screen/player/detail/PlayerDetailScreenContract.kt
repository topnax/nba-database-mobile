package com.github.topnax.nbadatabasemobile.presentation.screen.player.detail

import com.github.topnax.nbadatabasemobile.data.Player


interface PlayerDetailScreenContract {
    data class State(
        val playerFullName: String,
        val player: Player? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )

    sealed interface Event {
        data object ReloadPlayer : Event
    }
}