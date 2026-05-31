package com.example.composeanimations.data.network

import com.example.composeanimations.data.planets.remote.SolarSystemApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * Builds the networking stack: an OkHttp client (with request logging) and a Retrofit instance
 * wired to kotlinx.serialization. Kept as a plain object so the app has zero DI-framework
 * dependencies; in a larger codebase these `provide*` functions map directly onto Hilt/Koin
 * modules.
 */
object NetworkModule {

    private val json = Json {
        ignoreUnknownKeys = true   // tolerate fields we didn't model
        coerceInputValues = true   // fall back to defaults on null/mismatch
    }

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC },
            )
            .build()

    fun provideSolarSystemApi(): SolarSystemApi =
        Retrofit.Builder()
            .baseUrl(SolarSystemApi.BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(SolarSystemApi::class.java)
}
