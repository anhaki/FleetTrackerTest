package com.haki.fleettrackertest.core.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetVehicleMovementUseCase @Inject constructor() {
    suspend operator fun invoke(route: List<LatLng>, onUpdate: (LatLng) -> Unit) {
        route.forEach { position ->
            delay(1000)
            onUpdate(position)
        }
    }
}