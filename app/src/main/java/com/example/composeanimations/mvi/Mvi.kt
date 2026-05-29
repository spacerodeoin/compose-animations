package com.example.composeanimations.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Marker for an immutable UI state. The single source of truth a screen renders from.
 */
interface UiState

/**
 * Marker for a user/system intent. The only way state is allowed to change.
 */
interface UiIntent

/**
 * Marker for a one-shot side effect (navigation, snackbar) that must not survive recomposition.
 */
interface UiEffect

/**
 * Minimal MVI core.
 *
 * Unidirectional data flow:
 *
 *   View --(Intent)--> ViewModel --(reduce)--> State --> View
 *                                  \--(Effect)--> View (one-shot)
 *
 * Subclasses provide the initial state and the reducer in [handleIntent].
 */
abstract class MviViewModel<S : UiState, I : UiIntent, E : UiEffect>(
    initialState: S
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effects = Channel<E>(Channel.BUFFERED)
    val effects: Flow<E> = _effects.receiveAsFlow()

    val currentState: S get() = _state.value

    /** Entry point from the View. */
    fun onIntent(intent: I) = handleIntent(intent)

    /** Reduce an intent into a new state and/or emit effects. */
    protected abstract fun handleIntent(intent: I)

    /** Apply an immutable transform to the current state. */
    protected fun setState(reducer: S.() -> S) {
        _state.update(reducer)
    }

    protected fun sendEffect(effect: E) {
        viewModelScope.launch { _effects.send(effect) }
    }
}
