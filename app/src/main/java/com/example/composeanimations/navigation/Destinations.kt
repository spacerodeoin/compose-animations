package com.example.composeanimations.navigation

/**
 * Type-safe list of every screen route in the app.
 */
enum class Destination(val route: String, val title: String) {
    Home("home", "Physics with Animation"),
    Scale("scale", "Scale — Hooke's Law"),
    Translation("translation", "Translation — Free Fall"),
    Rotation("rotation", "Rotation — Angular Motion"),
    Alpha("alpha", "Alpha — Exponential Decay"),
    Planets("planets", "Planets — Surface Gravity");

    companion object {
        /** The demos shown on the home grid (Home excluded). */
        val demos = listOf(Scale, Translation, Rotation, Alpha, Planets)
    }
}
