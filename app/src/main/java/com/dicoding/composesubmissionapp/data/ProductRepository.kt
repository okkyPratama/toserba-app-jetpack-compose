package com.dicoding.composesubmissionapp.data

import com.dicoding.composesubmissionapp.model.Product
import com.dicoding.composesubmissionapp.model.ProductOrder
import com.dicoding.composesubmissionapp.model.ProductsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ProductRepository {

    private val productOrder = mutableListOf<ProductOrder>()

    init {
        if (productOrder.isEmpty()) {
            ProductsData.products.forEach { product ->
                productOrder.add(ProductOrder(product, 0))
            }
        }
    }

    fun getAllProduct(): Flow<List<ProductOrder>> {
        return flowOf(productOrder)
    }

    fun searchProduct(query: String): List<Product>{
        return ProductsData.products.filter {
              it.title.contains(query, ignoreCase = true)
        }
    }

    fun getProductById(productId: Long): ProductOrder {
        return productOrder.first {
            it.product.id == productId
        }
    }

    fun updateProductOrder(productId: Long, newCountValue: Int): Flow<Boolean> {
        val index = productOrder.indexOfFirst { it.product.id == productId }
        val result = if (index >= 0) {
            val productOrderItem = productOrder[index]
            productOrder[index] =
                productOrderItem.copy(product = productOrderItem.product, count = newCountValue)

            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedProductOrder(): Flow<List<ProductOrder>> {
        return getAllProduct()
            .map { productOrder ->
                productOrder.filter { order ->
                    order.count != 0
                }
            }
    }


    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(): ProductRepository =
            instance ?: synchronized(this) {
                ProductRepository().apply {
                    instance = this
                }
            }
    }


}