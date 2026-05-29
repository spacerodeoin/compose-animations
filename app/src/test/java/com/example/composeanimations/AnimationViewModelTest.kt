package com.example.composeanimations

import com.example.composeanimations.ui.alpha.AlphaContract
import com.example.composeanimations.ui.alpha.AlphaViewModel
import com.example.composeanimations.ui.rotation.RotationContract
import com.example.composeanimations.ui.rotation.RotationViewModel
import com.example.composeanimations.ui.scale.ScaleContract
import com.example.composeanimations.ui.scale.ScaleViewModel
import com.example.composeanimations.ui.translation.TranslationContract
import com.example.composeanimations.ui.translation.TranslationViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Reducers for the four demo screens must bump [playId] on every Replay intent so the View
 * re-launches its animation. These are pure, synchronous state transitions — no DB, no I/O.
 */
class AnimationViewModelTest {

    @Test
    fun `scale replay increments playId`() {
        val vm = ScaleViewModel()
        assertEquals(0, vm.currentState.playId)
        vm.onIntent(ScaleContract.Intent.Replay)
        vm.onIntent(ScaleContract.Intent.Replay)
        assertEquals(2, vm.currentState.playId)
    }

    @Test
    fun `translation replay increments playId`() {
        val vm = TranslationViewModel()
        vm.onIntent(TranslationContract.Intent.Replay)
        assertEquals(1, vm.currentState.playId)
    }

    @Test
    fun `rotation replay increments playId`() {
        val vm = RotationViewModel()
        vm.onIntent(RotationContract.Intent.Replay)
        assertEquals(1, vm.currentState.playId)
    }

    @Test
    fun `alpha replay increments playId`() {
        val vm = AlphaViewModel()
        vm.onIntent(AlphaContract.Intent.Replay)
        assertEquals(1, vm.currentState.playId)
    }
}
