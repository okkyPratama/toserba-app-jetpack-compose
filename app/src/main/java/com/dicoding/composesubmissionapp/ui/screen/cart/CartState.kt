package com.dicoding.composesubmissionapp.ui.screen.cart

import com.dicoding.composesubmissionapp.model.ProductOrder

data class CartState(
    val productOrder: List<ProductOrder>,
    val totalPrice: Int
)