package com.haki.fleettrackertest.core.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.haki.fleettrackertest.core.domain.model.Route
import com.haki.fleettrackertest.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IFleetRepository {
    suspend fun getFullRoute(): Flow<List<LatLng>>
}