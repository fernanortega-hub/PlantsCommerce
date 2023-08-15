package com.fernanortega.plantscommerce.presentation.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    windowSizeClass: WindowSizeClass
) {
    navigation(startDestination = Routes.Login.route, route = Routes.Auth.route) {
        composable(
            route = Routes.Login.route
        ) {

        }

        composable(
            route = Routes.Register.route
        ) {

        }

        composable(
            route = Routes.ForgotPassword.route
        ) {

        }
    }
}
