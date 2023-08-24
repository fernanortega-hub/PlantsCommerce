package com.fernanortega.plantscommerce.presentation.ui.navigation.utils

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateToMenu(options: NavOptions? = null) {
    this.navigate(Routes.Menu.route, options)
}

fun NavController.navigateToShoppingCart(options: NavOptions? = null) {
    this.navigate(Routes.ShoppingCart.route, options)
}

fun NavController.navigateToProfile(options: NavOptions? = null) {
    this.navigate(Routes.Profile.route, options)
}

fun NavController.navigateToSearch(options: NavOptions? = null) {
    this.navigate(Routes.Search.route, options)
}

fun NavController.navigateToLogin(options: NavOptions? = null) {
    this.navigate(Routes.Login.route, options)
}

fun NavController.navigateToRegister(options: NavOptions? = null) {
    this.navigate(Routes.Register.route, options)
}