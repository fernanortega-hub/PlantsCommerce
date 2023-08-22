package com.fernanortega.plantscommerce.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.plantscommerce.data.di.RetrofitModule
import com.fernanortega.plantscommerce.data.repositories.AuthRepository
import com.fernanortega.plantscommerce.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.OnEmailChange -> _uiState.update { state -> state.copy(email = event.email) }
            LoginEvent.OnLoginClick -> {
                viewModelScope.launch {
                    val response = authRepository.login(_uiState.value.email, _uiState.value.password)

                    Log.i("response", response.toString())
                    Log.i("token", RetrofitModule.token)
                }
            }
            is LoginEvent.OnPasswordChange -> _uiState.update { state -> state.copy(password = event.password) }
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isUiLoading: Boolean = false,
    val toast: UiText = UiText.UnformattedString("")
)

sealed interface LoginEvent {
    data class OnEmailChange(val email: String): LoginEvent
    data class OnPasswordChange(val password: String): LoginEvent
    object OnLoginClick: LoginEvent

}
