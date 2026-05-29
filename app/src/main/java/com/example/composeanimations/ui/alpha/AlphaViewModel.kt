package com.example.composeanimations.ui.alpha

import com.example.composeanimations.mvi.MviViewModel

class AlphaViewModel : MviViewModel<AlphaContract.State, AlphaContract.Intent, AlphaContract.Effect>(
    initialState = AlphaContract.State()
) {
    override fun handleIntent(intent: AlphaContract.Intent) {
        when (intent) {
            AlphaContract.Intent.Replay -> setState { copy(playId = playId + 1) }
        }
    }
}
