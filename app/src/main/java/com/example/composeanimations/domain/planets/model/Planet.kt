package com.example.composeanimations.domain.planets.model

/**
 * A solar-system planet as the app cares about it — a pure domain model with no knowledge of
 * JSON, Retrofit, or Compose. The data layer maps wire DTOs into this; the UI layer renders it.
 */
data class Planet(
    val id: String,
    val name: String,
    /** Surface gravity in m/s². */
    val gravity: Double,
    /** Mass in kilograms. */
    val massKg: Double,
    /** Mean radius in kilometres. */
    val meanRadiusKm: Double,
) {
    /** Surface gravity expressed as a multiple of Earth's (g₀ = 9.80665 m/s²). */
    val gravityRelativeToEarth: Double
        get() = gravity / EARTH_GRAVITY

    companion object {
        const val EARTH_GRAVITY = 9.80665
    }
}
