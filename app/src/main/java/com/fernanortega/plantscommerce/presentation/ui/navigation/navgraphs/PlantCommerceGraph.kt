package com.fernanortega.plantscommerce.presentation.ui.navigation.navgraphs

import android.widget.Toast
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fernanortega.plantscommerce.presentation.ui.PlantsCommerceState
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.TopLevelDestination
import com.fernanortega.plantscommerce.presentation.ui.screens.menu.MainMenuScreen
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.MenuViewModel

fun NavGraphBuilder.plantCommerceGraph(
    navController: NavHostController,
    appState: PlantsCommerceState
) {
    composable(
        route = Routes.Menu.route
    ) {
        val viewModel: MenuViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val toastMessage = uiState.toast?.asString()

        LaunchedEffect(toastMessage) {
            if(toastMessage != null) {
                Toast.makeText(appState.context, toastMessage, Toast.LENGTH_SHORT).show()
                viewModel.dismissToast()
            }
        }

        LaunchedEffect(uiState.shouldReLogin) {
            if(uiState.shouldReLogin) {
                appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN)
            }
        }

        MainMenuScreen(
            isInLandscape = appState.isInLandscape,
            isCompactScreen = appState.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact,
            uiState = uiState,
            onEvent = {

            }
        )
    }
}


