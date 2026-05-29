package com.example.composeanimations.ui.home

import com.example.composeanimations.mvi.UiEffect
import com.example.composeanimations.mvi.UiIntent
import com.example.composeanimations.mvi.UiState
import com.example.composeanimations.navigation.Destination

object HomeContract {

    data class State(
        val demos: List<Destination> = Destination.demos,
    ) : UiState

    sealed interface Intent : UiIntent {
        data class DemoClicked(val destination: Destination) : Intent
    }

    sealed interface Effect : UiEffect {
        data class NavigateTo(val destination: Destination) : Effect
    }
}
