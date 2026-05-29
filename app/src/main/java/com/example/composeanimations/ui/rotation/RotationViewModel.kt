package com.example.composeanimations.ui.rotation

import com.example.composeanimations.mvi.MviViewModel

class RotationViewModel :
    MviViewModel<RotationContract.State, RotationContract.Intent, RotationContract.Effect>(
        initialState = RotationContract.State()
    ) {
    override fun handleIntent(intent: RotationContract.Intent) {
        when (intent) {
            RotationContract.Intent.Replay -> setState { copy(playId = playId + 1) }
        }
    }
}
