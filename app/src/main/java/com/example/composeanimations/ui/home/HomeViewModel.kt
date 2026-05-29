package com.example.composeanimations.ui.home

import com.example.composeanimations.mvi.MviViewModel

class HomeViewModel : MviViewModel<HomeContract.State, HomeContract.Intent, HomeContract.Effect>(
    initialState = HomeContract.State()
) {
    override fun handleIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.DemoClicked ->
                sendEffect(HomeContract.Effect.NavigateTo(intent.destination))
        }
    }
}
