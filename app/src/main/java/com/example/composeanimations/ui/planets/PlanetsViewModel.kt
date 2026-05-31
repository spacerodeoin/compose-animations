package com.example.composeanimations.ui.planets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composeanimations.di.ServiceLocator
import com.example.composeanimations.domain.planets.GetPlanetsUseCase
import com.example.composeanimations.mvi.MviViewModel
import kotlinx.coroutines.launch

/**
 * Reduces [PlanetsContract.Intent]s into [PlanetsContract.State] by driving the
 * [GetPlanetsUseCase]. It depends on the domain use case (an abstraction), not on Retrofit, so
 * it can be unit-tested with a fake repository and no network.
 */
class PlanetsViewModel(
    private val getPlanets: GetPlanetsUseCase,
) : MviViewModel<PlanetsContract.State, PlanetsContract.Intent, PlanetsContract.Effect>(
    initialState = PlanetsContract.State(),
) {

    init {
        onIntent(PlanetsContract.Intent.Load)
    }

    override fun handleIntent(intent: PlanetsContract.Intent) {
        when (intent) {
            PlanetsContract.Intent.Load,
            PlanetsContract.Intent.Retry -> loadPlanets()
        }
    }

    private fun loadPlanets() {
        if (currentState.isLoading) return
        setState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getPlanets()
                .onSuccess { planets ->
                    setState { copy(isLoading = false, planets = planets, error = null) }
                }
                .onFailure { throwable ->
                    val message = throwable.message ?: "Could not load planets"
                    setState { copy(isLoading = false, error = message) }
                    sendEffect(PlanetsContract.Effect.ShowMessage(message))
                }
        }
    }

    /**
     * Supplies the use case to the no-arg `viewModel()` factory used by Compose. This is the
     * seam where DI plugs in; swapping [ServiceLocator] for Hilt would only change this class.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                PlanetsViewModel(ServiceLocator.getPlanetsUseCase) as T
        }
    }
}
