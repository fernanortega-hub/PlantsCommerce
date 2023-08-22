package com.fernanortega.plantscommerce.presentation.ui.navigation.utils

sealed class Routes(val route: String) {
    object Login: Routes("login")
    object Register: Routes("register")
    object ForgotPassword: Routes("forgot_password")
    object Auth: Routes("auth")
    object Menu: Routes("menu")
    object ShoppingCart: Routes("shopping_cart")
    object Profile: Routes("profile")
    object Search: Routes("search")
}
