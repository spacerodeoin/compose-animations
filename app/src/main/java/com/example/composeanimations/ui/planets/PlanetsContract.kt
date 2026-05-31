package com.example.composeanimations.ui.planets

import com.example.composeanimations.domain.planets.model.Planet
import com.example.composeanimations.mvi.UiEffect
import com.example.composeanimations.mvi.UiIntent
import com.example.composeanimations.mvi.UiState

/**
 * The MVI contract for the Planets screen: the immutable [State] the UI renders, the [Intent]s
 * the user can fire, and the one-shot [Effect]s the ViewModel emits.
 */
object PlanetsContract {

    data class State(
        val isLoading: Boolean = false,
        val planets: List<Planet> = emptyList(),
        val error: String? = null,
    ) : UiState

    sealed interface Intent : UiIntent {
        data object Load : Intent
        data object Retry : Intent
    }

    sealed interface Effect : UiEffect {
        data class ShowMessage(val text: String) : Effect
    }
}
