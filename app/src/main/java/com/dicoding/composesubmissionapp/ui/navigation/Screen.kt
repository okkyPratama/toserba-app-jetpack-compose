package com.dicoding.composesubmissionapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object About : Screen("about")
    object DetailProduct : Screen("detail/{productId}") {
        fun createRoute(productId: Long) = "detail/$productId"
    }
}
