package com.dicoding.composesubmissionapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.dicoding.composesubmissionapp.data.ProductRepository
import com.dicoding.composesubmissionapp.model.Product
import com.dicoding.composesubmissionapp.model.ProductOrder
import com.dicoding.composesubmissionapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailProductViewModel(
    private val repository: ProductRepository

): ViewModel(){
    private val _uiState: MutableStateFlow<UiState<ProductOrder>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ProductOrder>>
        get() = _uiState

    fun getProductById(productId: Long) {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getProductById(productId))
    }

    fun addToCart(product: Product, count: Int) {
        repository.updateProductOrder(product.id, count)
    }


}