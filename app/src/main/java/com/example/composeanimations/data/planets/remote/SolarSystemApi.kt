package com.example.composeanimations.data.planets.remote

import com.example.composeanimations.data.planets.remote.dto.BodiesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit description of the public Solar System OpenData REST API (no auth, HTTPS).
 *
 * `filter[]` restricts the result to planets; `data` trims the payload to just the fields we
 * map, keeping the response small. Both default here so callers need no arguments.
 */
interface SolarSystemApi {

    @GET("rest/bodies/")
    suspend fun getBodies(
        @Query("filter[]") filter: String = "isPlanet,eq,true",
        @Query("data") fields: String = "id,englishName,gravity,meanRadius,mass",
    ): BodiesResponseDto

    companion object {
        const val BASE_URL = "https://api.le-systeme-solaire.net/"
    }
}
