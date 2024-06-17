package com.dicoding.composesubmissionapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.composesubmissionapp.data.ProductRepository
import com.dicoding.composesubmissionapp.model.ProductOrder
import com.dicoding.composesubmissionapp.ui.common.UiState
import kotlinx.coroutines.flow.*

class HomeViewModel(
    private val repository: ProductRepository

): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ProductOrder>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ProductOrder>>>
        get() = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    init {
        getAllProducts()
    }

fun getAllProducts() {
    _query.flatMapLatest { query ->
        if (query.isEmpty()) {
            repository.getAllProduct()
        } else {
            flow {
                val products = repository.searchProduct(query)
                val productOrders = products.map { product ->
                    repository.getProductById(product.id)
                }
                emit(productOrders)
            }
        }
    }
        .catch {
            _uiState.value = UiState.Error(it.message.toString())
        }
        .onEach { productOrders ->
            _uiState.value = UiState.Success(productOrders)
        }
        .launchIn(viewModelScope)
}

    fun searchProducts(query: String) {
        _query.value = query
    }




}