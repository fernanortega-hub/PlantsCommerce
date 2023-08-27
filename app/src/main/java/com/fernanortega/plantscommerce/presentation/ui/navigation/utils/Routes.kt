package com.fernanortega.plantscommerce.presentation.ui.navigation.utils

sealed class Routes(val route: String) {
    object Login: Routes("login")
    object Register: Routes("register")
    object ForgotPassword: Routes("forgot_password")
    object Auth: Routes("auth")
    object Shop: Routes("shop")
    object Menu: Routes("shop_menu")
    object ShoppingCart: Routes("shopping_cart")
    object Profile: Routes("profile")
    object Search: Routes("search")
    object ProductDetails: Routes("shop_product_details/{${PRODUCT_ID_KEY}}") {
        fun createRoute(productId: String) = "shop_product_details/$productId"
    }

    companion object {
        const val PRODUCT_ID_KEY = "product_id"
    }
}
