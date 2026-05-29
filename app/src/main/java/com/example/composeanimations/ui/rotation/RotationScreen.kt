package com.example.composeanimations.ui.rotation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeanimations.navigation.Destination
import com.example.composeanimations.ui.components.DemoScaffold

@Composable
fun RotationScreen(
    onBack: () -> Unit,
    viewModel: RotationViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val angle = remember { Animatable(0f) }

    // Torque spins the body up to speed (angular acceleration) and friction brings it back to
    // rest. FastOutSlowIn ramps angular velocity up, holds, then eases it down across 4 turns.
    LaunchedEffect(state.playId) {
        angle.snapTo(0f)
        angle.animateTo(
            targetValue = 1440f,
            animationSpec = tween(durationMillis = 2600, easing = FastOutSlowInEasing),
        )
    }

    DemoScaffold(
        title = Destination.Rotation.title,
        principle = "Angular Motion — τ = Iα",
        explanation = "A torque produces angular acceleration inversely proportional to the " +
            "body's moment of inertia. Here the square spins up from rest, reaches a peak angular " +
            "velocity, then decelerates as friction removes that rotational energy — four full " +
            "turns shaped by an accelerate-then-decelerate easing curve.",
        onBack = onBack,
        onReplay = { viewModel.onIntent(RotationContract.Intent.Replay) },
    ) { modifier ->
        Box(
            modifier = modifier
                .graphicsLayer { rotationZ = angle.value }
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary),
        )
    }
}
