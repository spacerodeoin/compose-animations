package com.example.composeanimations.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeanimations.ui.alpha.AlphaScreen
import com.example.composeanimations.ui.home.HomeScreen
import com.example.composeanimations.ui.planets.PlanetsScreen
import com.example.composeanimations.ui.rotation.RotationScreen
import com.example.composeanimations.ui.scale.ScaleScreen
import com.example.composeanimations.ui.translation.TranslationScreen

private const val MOTION_DURATION = 350

/**
 * Material "shared axis X" transition: the outgoing screen slides and fades out along the
 * x-axis while the incoming one slides and fades in — communicating a forward/back spatial
 * relationship between pages.
 */
private fun AnimatedContentTransitionScope<*>.enter(forward: Boolean) =
    slideInHorizontally(tween(MOTION_DURATION)) { full -> if (forward) full / 4 else -full / 4 } +
        fadeIn(tween(MOTION_DURATION))

private fun AnimatedContentTransitionScope<*>.exit(forward: Boolean) =
    slideOutHorizontally(tween(MOTION_DURATION)) { full -> if (forward) -full / 4 else full / 4 } +
        fadeOut(tween(MOTION_DURATION))

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
        enterTransition = { enter(forward = true) },
        exitTransition = { exit(forward = true) },
        popEnterTransition = { enter(forward = false) },
        popExitTransition = { exit(forward = false) },
    ) {
        composable(Destination.Home.route) {
            HomeScreen(onNavigate = { navController.navigate(it.route) })
        }
        composable(Destination.Scale.route) {
            ScaleScreen(onBack = { navController.popBackStack() })
        }
        composable(Destination.Translation.route) {
            TranslationScreen(onBack = { navController.popBackStack() })
        }
        composable(Destination.Rotation.route) {
            RotationScreen(onBack = { navController.popBackStack() })
        }
        composable(Destination.Alpha.route) {
            AlphaScreen(onBack = { navController.popBackStack() })
        }
        composable(Destination.Planets.route) {
            PlanetsScreen(onBack = { navController.popBackStack() })
        }
    }
}
