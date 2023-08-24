package com.fernanortega.plantscommerce.presentation.ui.navigation.navgraphs

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fernanortega.plantscommerce.data.di.RetrofitModule
import com.fernanortega.plantscommerce.presentation.ui.PlantsCommerceState
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.LoginViewModel
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.TopLevelDestination
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.navigateToRegister
import com.fernanortega.plantscommerce.presentation.ui.screens.auth.LoginScreen
import com.fernanortega.plantscommerce.presentation.ui.screens.auth.RegisterScreen
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.RegisterViewModel

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
        val context = LocalContext.current

        LaunchedEffect(uiState.token) {
            if(uiState.token.isNotBlank()) {
                appState.setToken(uiState.token)
            }
        }

        LaunchedEffect(uiState.toast) {
            if(uiState.toast != null) {
                Toast.makeText(context, uiState.toast!!.asString(context), Toast.LENGTH_SHORT).show()
                viewModel.dismissToast()
            }
        }

        LoginScreen(
            modifier = Modifier
                .fillMaxSize(),
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onRegisterNavigate = { appState.navigateToTopLevelDestination(TopLevelDestination.REGISTER) },
            isCompactScreen = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact,
            isInLandscape = appState.isInLandscape
        )
    }

    composable(
        route = Routes.Register.route
    ) {
        val viewModel: RegisterViewModel = hiltViewModel()

        val context = LocalContext.current

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(uiState.toast) {
            if(uiState.toast != null) {
                Toast.makeText(context, uiState.toast!!.asString(context), Toast.LENGTH_SHORT).show()
                viewModel.dismissToast()
            }
        }

        LaunchedEffect(uiState.navigateToLogin) {
            if(uiState.navigateToLogin == true) {
                appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN)
            }
        }

        RegisterScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            isCompactScreen = appState.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact,
            isInLandscape = appState.isInLandscape,
            navigateToLogin = { appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN) }
        )
    }
}
