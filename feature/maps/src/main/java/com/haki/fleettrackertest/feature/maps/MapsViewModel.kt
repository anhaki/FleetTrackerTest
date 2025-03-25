package com.haki.fleettrackertest.feature.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.haki.fleettrackertest.core.utils.VehicleState
import com.haki.fleettrackertest.core.domain.usecase.GetFullRouteUseCase
import com.haki.fleettrackertest.core.domain.usecase.GetVehicleMovementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getFullRouteUseCase: GetFullRouteUseCase,
    private val getVehicleMovementUseCase: GetVehicleMovementUseCase
) : ViewModel() {

    private val _vehicleState = MutableStateFlow(VehicleState())
    val vehicleState: StateFlow<VehicleState> = _vehicleState.asStateFlow()
    private var fullRoute = emptyList<LatLng>()

    init {
        viewModelScope.launch {
            getFullRouteUseCase.invoke().collectLatest {
                fullRoute = it
            }

            _vehicleState.value = _vehicleState.value.copy(
                fullPath = fullRoute
            )

            delay(2000)
            startVehicleSimulation()
        }
    }

    private fun startVehicleSimulation() {
        viewModelScope.launch {
            getVehicleMovementUseCase.invoke(fullRoute){position ->
                updateVehiclePosition(position)
            }
        }
    }

    private fun updateVehiclePosition(newPosition: LatLng) {
        _vehicleState.value = _vehicleState.value.copy(
            currentPosition = newPosition,
            traveledPath = _vehicleState.value.traveledPath + newPosition
        )
    }
}