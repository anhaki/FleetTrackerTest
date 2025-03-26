package com.haki.fleettrackertest.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haki.fleettrackertest.core.domain.usecase.GetUserSessionUseCase
import com.haki.fleettrackertest.core.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    fun updateValue(newValue: String){
        _username.value = newValue
    }

    fun updatePassword(newPassword: String){
        _password.value = newPassword
    }

    fun updateisError(newisError: Boolean){
        _isError.value = newisError
    }

    fun login(username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val loginSuccess = loginUseCase(username, password)
            updateisError(!loginSuccess)
            if (loginSuccess) onSuccess()
        }
    }
}
