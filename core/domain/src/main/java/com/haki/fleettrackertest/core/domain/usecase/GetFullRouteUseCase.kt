package com.haki.fleettrackertest.core.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFullRouteUseCase @Inject constructor(private val repository: IFleetRepository) {
    suspend operator fun invoke(): Flow<List<LatLng>> = repository.getFullRoute()
}