package com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.github.topnax.nbadatabasemobile.domain.team.TeamRepository
import com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail.TeamDetailScreenContract.Event
import com.github.topnax.nbadatabasemobile.presentation.screen.teamdetail.TeamDetailScreenContract.State
import com.github.topnax.nbadatabasemobile.presentation.viewmodel.ContractViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class TeamDetailScreenViewModel(
    private val teamId: String,
    teamName: String,
    private val teamRepository: TeamRepository
) : ContractViewModel<State, Event>() {

    private val _state = mutableStateOf(State(teamName = teamName))

    override val state = _state

    override fun processEvent(event: Event) {
        when (event) {
            Event.ReloadTeam -> reloadPlayer()
        }
    }

    private fun reloadPlayer() {
        updateState {
            _state.value = _state.value.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                val team = teamRepository.getTeamById(teamId)
                _state.value = _state.value.copy(team = team, isLoading = false)
            } catch (e: Exception) {
                Timber.w(e, "Failed to load player")
                _state.value = _state.value.copy(isError = true, isLoading = false)
            }
        }
    }
}