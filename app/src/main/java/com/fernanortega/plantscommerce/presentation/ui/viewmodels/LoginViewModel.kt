package com.fernanortega.plantscommerce.presentation.ui.viewmodels

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.domain.model.ValidationResult
import com.fernanortega.plantscommerce.domain.usecases.validators.EmailError
import com.fernanortega.plantscommerce.domain.usecases.validators.EmailValidatorUseCase
import com.fernanortega.plantscommerce.domain.usecases.LoginUseCase
import com.fernanortega.plantscommerce.domain.usecases.validators.PasswordError
import com.fernanortega.plantscommerce.domain.usecases.validators.PasswordValidatorUseCase
import com.fernanortega.plantscommerce.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> _uiState.update { state -> state.copy(email = event.email) }
            is LoginEvent.OnPasswordChange -> _uiState.update { state -> state.copy(password = event.password) }
            LoginEvent.OnTogglePassword -> _uiState.update { state -> state.copy(togglePassword = !state.togglePassword) }
            LoginEvent.OnLoginClick -> {
                viewModelScope.launch {
                    try {
                        _uiState.update { it.copy(isLoading = true) }
                        _uiState.update { state ->
                            state.copy(
                                emailError = validateEmail(state.email),
                                passwordError = validatePassword(state.password)
                            )
                        }

                        if(_uiState.value.emailError != null || _uiState.value.passwordError != null) {
                            _uiState.update { state ->
                                state.copy(
                                    toast = UiText.StringResource(R.string.check_your_credentials),
                                    isLoading = false
                                )
                            }
                            return@launch
                        }

                        val response =
                            loginUseCase.invoke(_uiState.value.email, _uiState.value.password)

                        if(!response.isSuccessful || response.data == null) {
                            _uiState.update { state ->
                                state.copy(
                                    toast = when(response.statusCode) {
                                        401, 403 -> UiText.StringResource(R.string.check_your_credentials)
                                        404 -> UiText.StringResource(R.string.user_not_found)
                                        else -> UiText.StringResource(R.string.general_error_label, response.message)
                                    },
                                    isLoading = false
                                )
                            }
                            return@launch
                        }

                        _uiState.update { state -> state.copy(token = response.data, isLoading = false) }
                    } catch (ex: Exception) {
                        _uiState.update { state ->
                            state.copy(
                                toast = UiText.StringResource(R.string.general_error_label, ex.message ?: ""),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun validatePassword(password: String): UiText? {
        return when(val passwordValidator = PasswordValidatorUseCase().validate(password)) {
            is ValidationResult.InvalidResult -> {
                when (passwordValidator.invalidResult) {
                    PasswordError.BLANK -> UiText.StringResource(
                        R.string.password_is_required
                    )

                    PasswordError.LOW_CHARACTERS -> UiText.StringResource(
                        R.string.password_requires_characters
                    )
                }
            }

            ValidationResult.Valid -> null
        }
    }

    private fun validateEmail(email: String): UiText? {
        return when(val emailValidator = EmailValidatorUseCase().validate(email)) {
            is ValidationResult.InvalidResult -> {
                when (emailValidator.invalidResult) {
                    EmailError.BLANK -> UiText.StringResource(
                        R.string.email_must_be_not_empty_label
                    )

                    EmailError.NOT_MATCHES_EMAIL -> UiText.StringResource(
                        R.string.email_must_be_valid_email_label
                    )
                }
            }

            ValidationResult.Valid -> null
        }
    }

    fun dismissToast() {
        _uiState.update { it.copy(toast = null) }
    }
}

@Stable
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val isLoading: Boolean = false,
    val toast: UiText? = null,
    val token: String = "",
    val togglePassword: Boolean = false
)

@Stable
sealed interface LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent
    object OnLoginClick : LoginEvent
    object OnTogglePassword : LoginEvent
}
