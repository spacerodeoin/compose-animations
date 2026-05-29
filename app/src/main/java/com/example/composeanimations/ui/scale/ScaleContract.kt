package com.example.composeanimations.ui.scale

import com.example.composeanimations.mvi.UiEffect
import com.example.composeanimations.mvi.UiIntent
import com.example.composeanimations.mvi.UiState

object ScaleContract {

    /** [playId] is bumped on every replay so the View restarts its animation. */
    data class State(val playId: Int = 0) : UiState

    sealed interface Intent : UiIntent {
        data object Replay : Intent
    }

    sealed interface Effect : UiEffect
}
