package com.example.composeanimations.domain.planets

import com.example.composeanimations.domain.planets.model.Planet

/**
 * Single, well-named unit of business logic: fetch the planets and order them by surface
 * gravity, strongest first. Keeping the ordering rule here (rather than in the ViewModel or
 * repository) is what makes it reusable and trivially unit-testable.
 */
class GetPlanetsUseCase(
    private val repository: PlanetRepository,
) {
    suspend operator fun invoke(): Result<List<Planet>> =
        repository.getPlanets().map { planets ->
            planets.sortedByDescending(Planet::gravity)
        }
}
