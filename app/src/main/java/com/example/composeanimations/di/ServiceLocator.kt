package com.example.composeanimations.di

import com.example.composeanimations.data.network.NetworkModule
import com.example.composeanimations.data.planets.PlanetRepositoryImpl
import com.example.composeanimations.domain.planets.GetPlanetsUseCase
import com.example.composeanimations.domain.planets.PlanetRepository

/**
 * The app's single composition root. Each layer is built once and the concrete data-layer types
 * are bound to their domain interfaces here — the one place the dependency graph is assembled.
 *
 * This is deliberately a hand-rolled service locator to keep the project framework-free and
 * readable; in production you would replace it with Hilt (`@Module`/`@Provides`) or Koin without
 * touching any of the layers above, because they only ever see the interfaces.
 */
object ServiceLocator {

    private val planetRepository: PlanetRepository by lazy {
        PlanetRepositoryImpl(NetworkModule.provideSolarSystemApi())
    }

    val getPlanetsUseCase: GetPlanetsUseCase by lazy {
        GetPlanetsUseCase(planetRepository)
    }
}
