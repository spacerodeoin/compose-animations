package com.example.composeanimations.ui.scale

import com.example.composeanimations.mvi.MviViewModel

class ScaleViewModel : MviViewModel<ScaleContract.State, ScaleContract.Intent, ScaleContract.Effect>(
    initialState = ScaleContract.State()
) {
    override fun handleIntent(intent: ScaleContract.Intent) {
        when (intent) {
            ScaleContract.Intent.Replay -> setState { copy(playId = playId + 1) }
        }
    }
}
