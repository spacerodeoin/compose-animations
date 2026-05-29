package com.example.composeanimations.ui.scale

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
fun ScaleScreen(
    onBack: () -> Unit,
    viewModel: ScaleViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scale = remember { Animatable(1f) }

    // A spring with low damping overshoots its rest length and oscillates before settling —
    // exactly how a real spring obeying Hooke's law (F = -kx) behaves.
    LaunchedEffect(state.playId) {
        scale.snapTo(0.3f)
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow,
            ),
        )
    }

    DemoScaffold(
        title = Destination.Scale.title,
        principle = "Hooke's Law — F = −kx",
        explanation = "A spring pulls back with a force proportional to how far it is stretched " +
            "or compressed. Released from rest, it overshoots equilibrium and oscillates with " +
            "decreasing amplitude. The bouncy scale uses a low-damping spring spec to mirror " +
            "that restoring force and the energy it sheds to damping on each swing.",
        onBack = onBack,
        onReplay = { viewModel.onIntent(ScaleContract.Intent.Replay) },
    ) { modifier ->
        Box(
            modifier = modifier
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primary),
        )
    }
}
