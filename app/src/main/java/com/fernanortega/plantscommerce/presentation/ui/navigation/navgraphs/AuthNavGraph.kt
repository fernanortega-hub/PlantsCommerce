package com.fernanortega.plantscommerce.presentation.ui.navigation.navgraphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fernanortega.plantscommerce.presentation.ui.PlantsCommerceState
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.LoginViewModel
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.navigateToRegister
import com.fernanortega.plantscommerce.presentation.ui.screens.auth.LoginScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    appState: PlantsCommerceState
) {
    val windowSizeClass = appState.windowSizeClass

    composable(
        route = Routes.Login.route
    ) {
        val viewModel: LoginViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LoginScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onRegisterNavigate = navController::navigateToRegister,
            isCompactScreen = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact,
            isInLandscape = appState.isInLandscape
        )
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
