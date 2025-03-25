package com.haki.fleettrackertest.core.data.source

import com.google.android.gms.maps.model.LatLng
import com.haki.fleettrackertest.core.data.source.remote.response.DirectionsResponse
import com.haki.fleettrackertest.core.domain.model.Route

object DataMapper {
    fun routeListToDomain(input: List<List<LatLng>>): Route = Route(routePoints = input)
}