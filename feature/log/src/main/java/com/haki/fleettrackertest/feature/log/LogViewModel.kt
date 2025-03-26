package com.haki.fleettrackertest.feature.log

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haki.fleettrackertest.core.domain.model.StatusLog
import com.haki.fleettrackertest.core.domain.usecase.GetAllStatusLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(
    private val getAllStatusLogUseCase: GetAllStatusLogUseCase
) : ViewModel() {

    var logsState = mutableStateOf<List<StatusLog>>(emptyList())
        private set

    init {
        getLogs()
    }

    private fun getLogs() {
        viewModelScope.launch {
            getAllStatusLogUseCase().collectLatest { result ->
                logsState.value = result
            }
        }
    }
}
