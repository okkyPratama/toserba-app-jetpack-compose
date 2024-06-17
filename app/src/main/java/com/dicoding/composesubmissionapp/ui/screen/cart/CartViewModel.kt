package com.dicoding.composesubmissionapp.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.composesubmissionapp.data.ProductRepository
import com.dicoding.composesubmissionapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: ProductRepository

): ViewModel(){
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedProductOrder() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedProductOrder()
                .collect { productOrder ->
                    val totalPrice =
                        productOrder.sumOf { it.product.price * it.count }
                    _uiState.value = UiState.Success(CartState(productOrder, totalPrice))
                }
        }
    }

    fun updateProductOrder(productId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateProductOrder(productId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedProductOrder()
                    }
                }
        }
    }

}