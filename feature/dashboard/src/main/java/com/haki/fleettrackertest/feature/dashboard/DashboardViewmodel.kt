package com.haki.fleettrackertest.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haki.fleettrackertest.core.domain.usecase.GetLastStatusLogUseCase
import com.haki.fleettrackertest.core.domain.usecase.GetUserSessionUseCase
import com.haki.fleettrackertest.core.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel @Inject constructor(
    private val getUserSessionUseCase: GetUserSessionUseCase,
    private val getLastStatusLogUseCase: GetLastStatusLogUseCase
) : ViewModel() {
    private val _speedState = MutableStateFlow(0)
    val speedState: StateFlow<Int> = _speedState.asStateFlow()

    private val _engineState = MutableStateFlow(Status(
        icon = R.drawable.ic_gear,
        name = "Engine",
        status = false,
        positiveStatusText = "On",
        negativeStatusText = "Off",
    ))
    val engineState: StateFlow<Status> = _engineState.asStateFlow()

    private val _doorState = MutableStateFlow(Status(
        icon = R.drawable.ic_door,
        name = "Door",
        status = false,
        positiveStatusText = "Closed",
        negativeStatusText = "Opened",
    ))
    val doorState: StateFlow<Status> = _doorState.asStateFlow()

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    private val _isCheckingSession = MutableStateFlow(true)
    val isCheckingSession: StateFlow<Boolean> = _isCheckingSession.asStateFlow()

    init {
        checkSession()
        initialStatus()
    }

    fun updateEngineStatus(newStatus: Boolean){
        _engineState.value = _engineState.value.copy(
            status = newStatus
        )
    }

    fun updateDoorStatus(newStatus: Boolean){
        _doorState.value = _doorState.value.copy(
            status = newStatus
        )
    }

    fun updateSpeed(newSpeed: Int) {
        _speedState.value = newSpeed
    }

    private fun initialStatus(){
        viewModelScope.launch {
                getLastStatusLogUseCase().collectLatest {
                _speedState.value = it.speed
                _engineState.value = _engineState.value.copy(
                    status = it.engineOn
                )
                _doorState.value = _doorState.value.copy(
                    status = it.doorClosed
                )
            }
        }
    }

    private fun checkSession() {
        viewModelScope.launch {
            getUserSessionUseCase().collect { user ->
                if (user.username.isBlank()) {
                    _isUserLoggedIn.value = false
                }
                _isCheckingSession.value = false
            }
        }
    }

}
