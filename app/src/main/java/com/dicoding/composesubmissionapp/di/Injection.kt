package com.dicoding.composesubmissionapp.di

import com.dicoding.composesubmissionapp.data.ProductRepository

object Injection {
    fun provideRepository(): ProductRepository {
        return ProductRepository.getInstance()
    }
}