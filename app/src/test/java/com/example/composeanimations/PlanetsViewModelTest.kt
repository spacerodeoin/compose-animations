package com.example.composeanimations

import com.example.composeanimations.domain.planets.GetPlanetsUseCase
import com.example.composeanimations.domain.planets.PlanetRepository
import com.example.composeanimations.domain.planets.model.Planet
import com.example.composeanimations.ui.planets.PlanetsContract
import com.example.composeanimations.ui.planets.PlanetsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.yield
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetsViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val earth =
        Planet(id = "earth", name = "Earth", gravity = 9.81, massKg = 0.0, meanRadiusKm = 0.0)

    private fun useCase(response: Result<List<Planet>>) =
        GetPlanetsUseCase(object : PlanetRepository {
            override suspend fun getPlanets() = response
        })

    @Test
    fun `loads planets into state on init`() = runTest {
        val vm = PlanetsViewModel(useCase(Result.success(listOf(earth))))

        assertFalse(vm.currentState.isLoading)
        assertEquals(listOf(earth), vm.currentState.planets)
        assertNull(vm.currentState.error)
    }

    @Test
    fun `failure populates error and emits message effect`() = runTest {
        val effects = mutableListOf<PlanetsContract.Effect>()
        val vm = PlanetsViewModel(useCase(Result.failure(RuntimeException("offline"))))
        val job = launch { vm.effects.collect { effects.add(it) } }
        yield()
        job.cancel()

        assertEquals("offline", vm.currentState.error)
        assertEquals(PlanetsContract.Effect.ShowMessage("offline"), effects.single())
    }
}
