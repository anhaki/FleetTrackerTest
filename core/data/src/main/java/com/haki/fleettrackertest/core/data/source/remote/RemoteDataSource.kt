package com.haki.fleettrackertest.core.data.source.remote

import com.google.android.gms.maps.model.LatLng
import com.haki.fleettrackertest.core.data.source.remote.response.DirectionsResponse
import com.haki.fleettrackertest.core.data.source.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getRoutes(origin: LatLng, destination: LatLng): List<DirectionsResponse.Route> =
        withContext(Dispatchers.IO) {
            apiService.getDirections(origin.toString(), destination.toString()).body()?.routes!!
        }
}

