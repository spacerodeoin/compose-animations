package com.example.composeanimations.domain.planets

import com.example.composeanimations.domain.planets.model.Planet

/**
 * Domain-owned contract for fetching planets. The interface lives in the domain layer so that
 * higher layers depend on an abstraction, not on Retrofit (dependency-inversion principle).
 * The concrete implementation lives in the data layer.
 *
 * Errors are returned as a [Result] rather than thrown, so callers handle the failure path
 * explicitly instead of leaking transport exceptions into the UI.
 */
interface PlanetRepository {
    suspend fun getPlanets(): Result<List<Planet>>
}
