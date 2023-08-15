package com.fernanortega.plantscommerce.presentation.ui.navigation

sealed class Routes(val route: String) {
    object Login: Routes("login")
    object Register: Routes("register")
    object ForgotPassword: Routes("forgot_password")
    object Auth: Routes("auth")
    object Feed: Routes("feed")
}
