package com.fernanortega.plantscommerce.presentation.ui.navigation.navgraphs

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fernanortega.plantscommerce.presentation.ui.PlantsCommerceState
import com.fernanortega.plantscommerce.presentation.ui.components.LoadingDialog
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.TopLevelDestination
import com.fernanortega.plantscommerce.presentation.ui.screens.shop.ProductDetailsScreen
import com.fernanortega.plantscommerce.presentation.ui.screens.shop.ShopScreen
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.MenuViewModel
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.ProductDetailViewModel

fun NavGraphBuilder.plantCommerceGraph(
    appState: PlantsCommerceState
) {
    navigation(
        route = Routes.Shop.route,
        startDestination = Routes.Menu.route
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

            LoadingDialog(
                show = uiState.isLoading
            )

            ShopScreen(
                isCompactScreen = appState.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact,
                uiState = uiState,
                onEvent = { },
                navigateToMoreDetails = { appState.navController.navigate(Routes.ProductDetails.createRoute(it)) }
            )
        }
        composable(
            route = Routes.ProductDetails.route,
            arguments = listOf(
                navArgument(
                    name = Routes.PRODUCT_ID_KEY,
                    builder = {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            )
        ) { entry ->
            val productId = requireNotNull(entry.arguments?.getString(Routes.PRODUCT_ID_KEY)) { "product id is required" }
            val viewModel: ProductDetailViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                viewModel.searchProduct(productId)
            }

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState.productFounded) {
                if(!uiState.productFounded) {
                    appState.navigateToTopLevelDestination(TopLevelDestination.SHOP)
                }
            }

            LaunchedEffect(uiState.toast) {
                if(uiState.toast != null) {
                    Toast.makeText(appState.context, uiState.toast!!.asString(appState.context), Toast.LENGTH_SHORT).show()
                    viewModel.dismissToast()
                }
            }

            LoadingDialog(
                show = uiState.isLoading
            )

            ProductDetailsScreen(
                modifier = Modifier
                    .fillMaxSize(),
                uiState = uiState,
                isCompactScreen  = appState.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact,
                onBackAction = appState.navController::popBackStack,
                isInLandscape = appState.isInLandscape
            )
        }
    }
}


