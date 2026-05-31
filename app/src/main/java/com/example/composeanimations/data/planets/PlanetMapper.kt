package com.example.composeanimations.data.planets

import com.example.composeanimations.data.planets.remote.dto.BodyDto
import com.example.composeanimations.domain.planets.model.Planet
import kotlin.math.pow

/**
 * Translates a wire [BodyDto] into the domain [Planet], collapsing the value/exponent mass
 * encoding into a single kilogram figure. Mapping lives in the data layer so the domain stays
 * ignorant of the API's shape.
 */
fun BodyDto.toDomain(): Planet = Planet(
    id = id,
    name = englishName,
    gravity = gravity,
    massKg = (mass?.massValue ?: 0.0) * 10.0.pow(mass?.massExponent ?: 0),
    meanRadiusKm = meanRadius,
)
