package com.haki.fleettrackertest.core.data.source.remote.network

import com.haki.fleettrackertest.core.data.source.remote.response.DirectionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.haki.fleettrackertest.core.data.BuildConfig

interface ApiService {
    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") originLatLng: String,
        @Query("destination") destinationLatLang: String,
        @Query("key") apiKey: String = BuildConfig.MAPS_API_KEY
    ): Response<DirectionsResponse>
}
