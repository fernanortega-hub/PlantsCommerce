package com.fernanortega.plantscommerce.presentation.ui.viewmodels

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.domain.model.User
import com.fernanortega.plantscommerce.domain.model.ValidationResult
import com.fernanortega.plantscommerce.domain.usecases.RegisterUseCase
import com.fernanortega.plantscommerce.domain.usecases.validators.EmailError
import com.fernanortega.plantscommerce.domain.usecases.validators.EmailValidatorUseCase
import com.fernanortega.plantscommerce.domain.usecases.validators.NameError
import com.fernanortega.plantscommerce.domain.usecases.validators.NameValidator
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
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnEmailChange -> {
                _uiState.update { state ->
                    state.copy(
                        emailError = emailValidator(event.email),
                        email = event.email
                    )
                }
            }

            is RegisterEvent.OnFirstNameChange -> {
                _uiState.update { state ->
                    state.copy(
                        firstNameError = firstNameValidator(event.firstName),
                        firstName = event.firstName
                    )
                }
            }

            is RegisterEvent.OnLastNameChange -> {
                _uiState.update { state ->
                    state.copy(
                        lastNameError = lastNameValidator(event.lastName),
                        lastName = event.lastName
                    )
                }
            }

            is RegisterEvent.OnPasswordChange -> {
                _uiState.update { state ->
                    state.copy(
                        passwordError = passwordValidator(event.password),
                        password = event.password
                    )
                }
            }

            RegisterEvent.OnRegisterClick -> registerUser()
            RegisterEvent.OnTogglePassword -> _uiState.update { state -> state.copy(togglePassword = !state.togglePassword) }
        }
    }

    private fun emailValidator(email: String): UiText? {
        return when (val validator =
            EmailValidatorUseCase().validate(email)) {
            is ValidationResult.InvalidResult -> {
                when (validator.invalidResult) {
                    EmailError.BLANK -> UiText.StringResource(R.string.email_must_be_not_empty_label)
                    EmailError.NOT_MATCHES_EMAIL -> UiText.StringResource(R.string.email_must_be_valid_email_label)
                }
            }

            ValidationResult.Valid -> null
        }
    }

    private fun passwordValidator(password: String): UiText? {
        return when (val validator =
            PasswordValidatorUseCase().validate(password)) {
            is ValidationResult.InvalidResult -> {
                when (validator.invalidResult) {
                    PasswordError.BLANK -> UiText.StringResource(R.string.password_is_required)
                    PasswordError.LOW_CHARACTERS -> UiText.StringResource(R.string.password_requires_characters)
                }
            }

            ValidationResult.Valid -> null
        }
    }

    private fun firstNameValidator(firstName: String): UiText? {
        return when (val validator =
            NameValidator().validate(firstName)) {
            is ValidationResult.InvalidResult -> {
                when (validator.invalidResult) {
                    NameError.BLANK -> UiText.StringResource(R.string.first_name_is_required)
                    NameError.HAS_SPECIAL_CHARACTERS -> UiText.StringResource(R.string.name_not_valid)
                }
            }

            ValidationResult.Valid -> null
        }
    }

    private fun lastNameValidator(lastName: String): UiText? {
        return when (val validator =
            NameValidator().validate(lastName)) {
            is ValidationResult.InvalidResult -> {
                when (validator.invalidResult) {
                    NameError.BLANK -> UiText.StringResource(R.string.first_name_is_required)
                    NameError.HAS_SPECIAL_CHARACTERS -> UiText.StringResource(R.string.name_not_valid)
                }
            }

            ValidationResult.Valid -> null
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            try {
                with(_uiState) {
                    update { state ->
                        state.copy(
                            emailError = emailValidator(state.email),
                            passwordError = passwordValidator(state.password),
                            firstNameError = firstNameValidator(state.firstName),
                            lastNameError = lastNameValidator(state.lastName)
                        )
                    }

                    if (value.passwordError != null || value.emailError != null || value.firstNameError != null || value.lastNameError != null) {
                        update { state ->
                            state.copy(
                                toast = UiText.StringResource(R.string.check_your_data)
                            )
                        }
                        return@launch
                    }

                    update { state ->
                        state.copy(
                            isLoading = true
                        )
                    }

                    val user = User(
                        email = value.email,
                        firstName = value.firstName,
                        lastName = value.lastName,
                        password = value.password,
                        role = "user"
                    )

                    val response = registerUseCase.invoke(user)

                    if (!response.isSuccessful || response.data == null) {
                        update { state ->
                            state.copy(
                                toast = UiText.StringResource(
                                    R.string.general_error_label,
                                    response.message
                                ),
                                isLoading = false
                            )
                        }
                        return@with
                    }

                    update { state ->
                        state.copy(
                            toast = UiText.StringResource(
                                R.string.welcome_user,
                                response.data.firstName + " " + response.data.lastName
                            ),
                            isLoading = false,
                            navigateToLogin = true
                        )
                    }
                }
            } catch (ex: Exception) {
                _uiState.update { state ->
                    state.copy(
                        toast = UiText.StringResource(
                            R.string.general_error_label,
                            ex.message ?: ""
                        ),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun dismissToast() {
        _uiState.update { state -> state.copy(toast = null) }
    }
}


@Stable
sealed interface RegisterEvent {
    data class OnFirstNameChange(val firstName: String) : RegisterEvent
    data class OnLastNameChange(val lastName: String) : RegisterEvent
    data class OnEmailChange(val email: String) : RegisterEvent
    data class OnPasswordChange(val password: String) : RegisterEvent
    object OnRegisterClick : RegisterEvent
    object OnTogglePassword : RegisterEvent
}

@Stable
data class RegisterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val firstNameError: UiText? = null,
    val lastNameError: UiText? = null,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val togglePassword: Boolean = false,
    val isLoading: Boolean = false,
    val toast: UiText? = null,
    val navigateToLogin: Boolean? = null
)