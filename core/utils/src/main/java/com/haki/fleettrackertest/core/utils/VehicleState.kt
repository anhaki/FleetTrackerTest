package com.haki.fleettrackertest.core.utils

import com.google.android.gms.maps.model.LatLng

data class VehicleState(
    val currentPosition: LatLng? = null,
    val traveledPath: List<LatLng> = emptyList(),
    val fullPath: List<LatLng> = emptyList()
)