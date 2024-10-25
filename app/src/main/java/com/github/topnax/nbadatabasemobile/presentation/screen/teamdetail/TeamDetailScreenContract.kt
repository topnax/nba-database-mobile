package com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail

import com.github.topnax.nbadatabasemobile.data.Team


interface TeamDetailScreenContract {
    data class State(
        val teamName: String,
        val team: Team? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )

    sealed interface Event {
        data object ReloadTeam : Event
    }
}