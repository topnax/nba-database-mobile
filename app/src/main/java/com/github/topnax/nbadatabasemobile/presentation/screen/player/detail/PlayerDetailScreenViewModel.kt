package com.github.topnax.nbadatabasemobile.presentation.screen.player.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.github.topnax.nbadatabasemobile.domain.player.PlayerRepository
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContract.Event
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContract.State
import com.github.topnax.nbadatabasemobile.presentation.viewmodel.ContractViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class PlayerDetailScreenViewModel(
    private val playerId: String,
    playerFullName: String,
    private val playerRepository: PlayerRepository
) : ContractViewModel<State, Event>() {

    private val _state = mutableStateOf(State(playerFullName = playerFullName))

    override val state = _state

    override fun processEvent(event: Event) {
        when (event) {
            Event.ReloadPlayer -> reloadPlayer()
        }
    }

    private fun reloadPlayer() {
        updateState {
            _state.value = _state.value.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                val player = playerRepository.getPlayerById(playerId)
                _state.value = _state.value.copy(player = player, isLoading = false)
            } catch (e: Exception) {
                Timber.w(e, "Failed to load player")
                _state.value = _state.value.copy(isError = true, isLoading = false)
            }
        }
    }
}