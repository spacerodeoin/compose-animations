package com.example.composeanimations

import com.example.composeanimations.navigation.Destination
import com.example.composeanimations.ui.home.HomeContract
import com.example.composeanimations.ui.home.HomeViewModel
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @Before
    fun setUp() {
        // viewModelScope dispatches on Main; redirect it to a test dispatcher for JVM tests.
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state exposes the four demos`() {
        val vm = HomeViewModel()
        assertEquals(Destination.demos, vm.currentState.demos)
    }

    @Test
    fun `demo click emits navigate effect for that destination`() = runTest {
        val vm = HomeViewModel()
        val effects = mutableListOf<HomeContract.Effect>()
        val job = launch { vm.effects.collect { effects.add(it) } }

        vm.onIntent(HomeContract.Intent.DemoClicked(Destination.Rotation))
        yield()
        job.cancel()

        assertEquals(
            HomeContract.Effect.NavigateTo(Destination.Rotation),
            effects.single(),
        )
    }
}
