package com.example.composeanimations.ui.alpha

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
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
import kotlin.math.exp

// N(t) = N₀·e^(−λt). The quantity remaining falls fast at first, then ever more slowly —
// it never quite reaches zero. animateTo(0) interpolates as 1 − easing(fraction), so this
// easing reproduces the e^(−λt) curve with λ ≈ 5.
private val DecayEasing = Easing { fraction -> 1f - exp(-5f * fraction) }

@Composable
fun AlphaScreen(
    onBack: () -> Unit,
    viewModel: AlphaViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val alpha = remember { Animatable(1f) }

    LaunchedEffect(state.playId) {
        alpha.snapTo(1f)
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 2200, easing = DecayEasing),
        )
    }

    DemoScaffold(
        title = Destination.Alpha.title,
        principle = "Exponential Decay — N(t) = N₀·e⁻ᵏᵗ",
        explanation = "Radioactive decay, capacitor discharge and damped sound all share one law: " +
            "the rate of change is proportional to the amount left, giving an exponential curve. " +
            "The circle's opacity drops sharply at first and then fades ever more gently, " +
            "approaching but never reaching full transparency.",
        onBack = onBack,
        onReplay = { viewModel.onIntent(AlphaContract.Intent.Replay) },
    ) { modifier ->
        Box(
            modifier = modifier
                .graphicsLayer { this.alpha = alpha.value }
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary),
        )
    }
}
