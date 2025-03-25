package com.haki.fleettrackertest.feature.dashboard

import androidx.lifecycle.ViewModel
import com.haki.fleettrackertest.core.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel @Inject constructor(
) : ViewModel() {
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

    init {

    }
}
