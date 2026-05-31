package com.example.composeanimations.data.planets

import com.example.composeanimations.data.planets.remote.SolarSystemApi
import com.example.composeanimations.domain.planets.PlanetRepository
import com.example.composeanimations.domain.planets.model.Planet

/**
 * Data-layer implementation of the domain [PlanetRepository]. It owns the API call and the
 * DTO→domain mapping, and converts any transport/parse exception into a failed [Result] so the
 * rest of the app deals only with success/failure values, never raw throwables.
 */
class PlanetRepositoryImpl(
    private val api: SolarSystemApi,
) : PlanetRepository {

    override suspend fun getPlanets(): Result<List<Planet>> = runCatching {
        api.getBodies().bodies.map { it.toDomain() }
    }
}
