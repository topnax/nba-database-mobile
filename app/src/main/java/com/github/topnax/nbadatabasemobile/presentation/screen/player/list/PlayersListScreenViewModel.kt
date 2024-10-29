package com.github.topnax.nbadatabasemobile.presentation.screen.player.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.github.topnax.nbadatabasemobile.domain.player.PlayerRepository
import com.github.topnax.nbadatabasemobile.implementation.paginator.DefaultPaginator
import com.github.topnax.nbadatabasemobile.presentation.screen.player.list.PlayersListViewModelScreenContract.Event
import com.github.topnax.nbadatabasemobile.presentation.screen.player.list.PlayersListViewModelScreenContract.State
import com.github.topnax.nbadatabasemobile.presentation.viewmodel.ContractViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class PlayersListViewScreenModel(
    private val playerRepository: PlayerRepository
) : ContractViewModel<State, Event>() {

    private val _state = mutableStateOf(State())

    override val state = _state

    private val pageSize = 35

    private val playerPaginator = DefaultPaginator(
        initialKey = 0,
        onLoadUpdated = { isLoading ->
            updateState {
                _state.value = it.copy(
                    isLoading = isLoading
                )
            }
        },
        onRequest = { key ->
            Timber.d("getting key: $key")
            playerRepository.getPlayers(key, pageSize)
        },
        getNextKey = { currentPage ->
            currentPage.nextCursor
        },
        onError = { error ->
            Timber.w(error, "Error loading players")
            updateState {
                _state.value = it.copy(
                    isError = true
                )
            }
        },
        onSuccess = { page, newKey ->
            Timber.d("new key: $newKey")
            updateState {
                _state.value = it.copy(
                    players = (it.players ?: emptyList()) + page.data,
                    isError = false
                )
            }
        },
        onEndReached = {
            updateState {
                _state.value = it.copy(
                    endReached = true
                )
            }
        }
    )

    override fun processEvent(event: Event) {
        when (event) {
            Event.LoadNextItems -> {
                viewModelScope.launch {
                    playerPaginator.loadNextItems()
                }
            }
            Event.Reload -> reload()
            Event.DismissError -> {
                updateState {
                    _state.value = it.copy(
                        isError = false
                    )
                }
            }
        }
    }


    private fun reload() {
        playerPaginator.reset()
        state.value = state.value.copy(
            players = null,
            endReached = false,
            isError = false
        )
        viewModelScope.launch {
            playerPaginator.loadNextItems()
        }
    }
}