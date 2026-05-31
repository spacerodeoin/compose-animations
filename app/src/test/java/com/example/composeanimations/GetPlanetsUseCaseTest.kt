package com.example.composeanimations

import com.example.composeanimations.domain.planets.GetPlanetsUseCase
import com.example.composeanimations.domain.planets.PlanetRepository
import com.example.composeanimations.domain.planets.model.Planet
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetPlanetsUseCaseTest {

    private fun planet(name: String, gravity: Double) =
        Planet(id = name, name = name, gravity = gravity, massKg = 0.0, meanRadiusKm = 0.0)

    @Test
    fun `orders planets by gravity strongest first`() = runTest {
        val repo = FakePlanetRepository(
            Result.success(
                listOf(
                    planet("Mars", 3.71),
                    planet("Jupiter", 24.79),
                    planet("Earth", 9.81),
                ),
            ),
        )

        val result = GetPlanetsUseCase(repo)().getOrThrow()

        assertEquals(listOf("Jupiter", "Earth", "Mars"), result.map(Planet::name))
    }

    @Test
    fun `propagates repository failure`() = runTest {
        val repo = FakePlanetRepository(Result.failure(RuntimeException("offline")))

        val result = GetPlanetsUseCase(repo)()

        assertTrue(result.isFailure)
    }

    private class FakePlanetRepository(
        private val response: Result<List<Planet>>,
    ) : PlanetRepository {
        override suspend fun getPlanets(): Result<List<Planet>> = response
    }
}
