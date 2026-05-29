package com.example.composeanimations.ui.translation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeanimations.navigation.Destination
import com.example.composeanimations.ui.components.DemoScaffold

/** s = ½gt² → distance grows with the square of time, so position eases in quadratically. */
private val FreeFallEasing = Easing { fraction -> fraction * fraction }

private const val DROP_DISTANCE_DP = 240f

@Composable
fun TranslationScreen(
    onBack: () -> Unit,
    viewModel: TranslationViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dropDp = remember { Animatable(-DROP_DISTANCE_DP / 2) }

    LaunchedEffect(state.playId) {
        // Fall: accelerate downward under "gravity".
        dropDp.snapTo(-DROP_DISTANCE_DP / 2)
        dropDp.animateTo(
            targetValue = DROP_DISTANCE_DP / 2,
            animationSpec = tween(durationMillis = 900, easing = FreeFallEasing),
        )
        // Impact: rebound upward then settle, a damped bounce as kinetic energy is returned
        // and then lost to the floor.
        dropDp.animateTo(
            targetValue = DROP_DISTANCE_DP / 2 - 60f,
            animationSpec = tween(durationMillis = 250),
        )
        dropDp.animateTo(
            targetValue = DROP_DISTANCE_DP / 2,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
            ),
        )
    }

    DemoScaffold(
        title = Destination.Translation.title,
        principle = "Free Fall — s = ½gt²",
        explanation = "Under constant gravitational acceleration an object's velocity rises " +
            "linearly while the distance fallen grows with the square of time. The ball starts " +
            "slow and visibly speeds up, driven by a quadratic easing curve, then settles with a " +
            "damped bounce as it dissipates energy at impact.",
        onBack = onBack,
        onReplay = { viewModel.onIntent(TranslationContract.Intent.Replay) },
    ) { modifier ->
        Box(
            modifier = modifier
                .graphicsLayer { translationY = dropDp.value * density }
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary),
        )
    }
}
