package com.example.composeanimations.data.planets.remote.dto

import kotlinx.serialization.Serializable

/**
 * Wire models mirroring the JSON returned by https://api.le-systeme-solaire.net/rest/bodies/.
 * These belong to the data layer only — they never cross into domain or UI. Defaults keep
 * deserialization resilient to missing fields, and `ignoreUnknownKeys` (see NetworkModule)
 * lets us request a handful of fields without listing the API's full schema.
 */
@Serializable
data class BodiesResponseDto(
    val bodies: List<BodyDto> = emptyList(),
)

@Serializable
data class BodyDto(
    val id: String,
    val englishName: String = "",
    /** Surface gravity, m/s². */
    val gravity: Double = 0.0,
    /** Mean radius, km. */
    val meanRadius: Double = 0.0,
    val mass: MassDto? = null,
)

/** The API encodes mass as value × 10^exponent to fit huge magnitudes into JSON. */
@Serializable
data class MassDto(
    val massValue: Double = 0.0,
    val massExponent: Int = 0,
)
