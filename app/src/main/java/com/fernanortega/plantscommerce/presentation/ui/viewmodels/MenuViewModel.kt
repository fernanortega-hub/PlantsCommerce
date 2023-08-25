package com.fernanortega.plantscommerce.presentation.ui.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.data.Constants
import com.fernanortega.plantscommerce.domain.model.User
import com.fernanortega.plantscommerce.domain.usecases.WhoAmIUseCase
import com.fernanortega.plantscommerce.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val whoAmIUseCase: WhoAmIUseCase,
    private val sharedPreferences: SharedPreferences
): ViewModel() {
    private val _uiState = MutableStateFlow(MainMenuUiState())
    val uiState = _uiState.asStateFlow()
    init {
        whoAmI()
    }

    private fun whoAmI() {
        viewModelScope.launch {
            val userId = sharedPreferences.getString(Constants.USER_ID_KEY, null)

            val response = whoAmIUseCase.invoke(userId)

            if(!response.isSuccessful || response.data == null) {
                _uiState.update { state ->
                    state.copy(
                        shouldReLogin = true,
                        toast = UiText.StringResource(R.string.session_expired)
                    )
                }
                return@launch
            }

            _uiState.update {
                it.copy(
                    user = response.data
                )
            }

            sharedPreferences.apply {
                edit().putString(Constants.USER_ID_KEY, response.data._id).apply()
            }
        }
    }

    fun dismissToast() {
        _uiState.update { it.copy(toast = null) }
    }

}

@Stable
data class MainMenuUiState(
    val user: User = User(),
    val plants: List<String> = emptyList(),
    val shouldReLogin: Boolean = false,
    val toast: UiText? = null
)
