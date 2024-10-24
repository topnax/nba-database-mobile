package com.github.topnax.nbadatabasemobile.presentation.screen.player.list

import com.github.topnax.nbadatabasemobile.data.Player

interface PlayersListViewModelScreenContract {
    data class State (
        val isLoading: Boolean = false,
        val players: List<Player>? = null,
        val isError: Boolean = false,
        val endReached: Boolean = false,
        val page: Int = 0,
        val nextPage: Int? = null,
    )

    sealed interface Event {
        data object LoadNextItems: Event
        data object DismissError: Event
        data object Reload: Event
    }
}