package com.example.composeanimations.ui.translation

import com.example.composeanimations.mvi.UiEffect
import com.example.composeanimations.mvi.UiIntent
import com.example.composeanimations.mvi.UiState

object TranslationContract {

    data class State(val playId: Int = 0) : UiState

    sealed interface Intent : UiIntent {
        data object Replay : Intent
    }

    sealed interface Effect : UiEffect
}
