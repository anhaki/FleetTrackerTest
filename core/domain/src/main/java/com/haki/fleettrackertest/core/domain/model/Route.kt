package com.haki.fleettrackertest.core.domain.model

import com.google.android.gms.maps.model.LatLng

data class Route(
    val routePoints: List<List<LatLng>>
)
