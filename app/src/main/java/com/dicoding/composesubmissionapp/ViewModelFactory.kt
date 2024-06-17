package com.dicoding.composesubmissionapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.composesubmissionapp.data.ProductRepository
import com.dicoding.composesubmissionapp.ui.screen.cart.CartViewModel
import com.dicoding.composesubmissionapp.ui.screen.detail.DetailProductViewModel
import com.dicoding.composesubmissionapp.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
            return DetailProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}