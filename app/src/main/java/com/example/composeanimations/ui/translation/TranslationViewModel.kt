package com.example.composeanimations.ui.translation

import com.example.composeanimations.mvi.MviViewModel

class TranslationViewModel :
    MviViewModel<TranslationContract.State, TranslationContract.Intent, TranslationContract.Effect>(
        initialState = TranslationContract.State()
    ) {
    override fun handleIntent(intent: TranslationContract.Intent) {
        when (intent) {
            TranslationContract.Intent.Replay -> setState { copy(playId = playId + 1) }
        }
    }
}
