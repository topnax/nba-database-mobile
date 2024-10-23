package com.github.topnax.nbadatabasemobile.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Use [updateState] method to update the state.
 */
abstract class ContractViewModel<State, Event> : ViewModel() {
    abstract val state: androidx.compose.runtime.State<State>

    fun onEvent(event: Event) {
        Timber.tag("EVENT").d("${this.viewModelName}: $event")
        processEvent(event)
    }

    protected abstract fun processEvent(event: Event)

    /**
     * Calls the [updateFx] function in the main thread.
     * State updates in vide models should only be made in via the [updateFx]
     * function.
     */
    protected fun updateState(updateFx: (State) -> Unit) {
        viewModelScope.launch {
            withContext(context = Dispatchers.Main) {
                updateFx.invoke(state.value)
                Timber.tag("STATE").d("${this@ContractViewModel.viewModelName}: ${state.value}")
            }
        }
    }

    private val viewModelName get() = this.javaClass.simpleName
}
