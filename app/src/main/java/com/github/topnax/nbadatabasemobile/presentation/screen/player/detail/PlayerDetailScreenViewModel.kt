package com.github.topnax.nbadatabasemobile.presentation.screen.player.detail

import androidx.compose.runtime.mutableStateOf
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContract.Event
import com.github.topnax.nbadatabasemobile.presentation.screen.player.detail.PlayerDetailScreenContract.State
import com.github.topnax.nbadatabasemobile.presentation.viewmodel.ContractViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class PlayerDetailScreenViewModel(val playerId: String) : ContractViewModel<State, Event>() {

    private val _state = mutableStateOf(State(playerId = playerId))

    override val state = _state

    override fun processEvent(event: Event) {
        TODO("Not yet implemented")
    }


}