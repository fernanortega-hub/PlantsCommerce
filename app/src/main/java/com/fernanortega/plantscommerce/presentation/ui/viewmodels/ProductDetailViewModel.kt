package com.fernanortega.plantscommerce.presentation.ui.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.domain.model.Product
import com.fernanortega.plantscommerce.domain.usecases.GetProductByIdUseCase
import com.fernanortega.plantscommerce.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun searchProduct(productId: String) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }

            val response = getProductByIdUseCase.invoke(productId)

            if(!response.isSuccessful || response.data == null) {
                val message = when(response.statusCode) {
                    404 -> UiText.StringResource(R.string.product_not_found)
                    else -> UiText.StringResource(R.string.general_error_label, response.message)
                }

                _uiState.update { state ->
                    state.copy(
                        toast = message,
                        isLoading = false,
                        productFounded = false
                    )
                }
                return@launch
            }

            _uiState.update { state ->
                state.copy(
                    product = response.data,
                    isLoading = false,
                    productFounded = true
                )
            }
        }
    }

    fun dismissToast() {
        _uiState.update { state -> state.copy(toast = null) }
    }
}

@Immutable
data class ProductDetailUiState(
    val product: Product = Product(),
    val isLoading: Boolean = false,
    val isSeeAllExpanded: Boolean? = null,
    val isSeeAllNecessary: Boolean = false,
    val toast: UiText? = null,
    val productFounded: Boolean = false
)
