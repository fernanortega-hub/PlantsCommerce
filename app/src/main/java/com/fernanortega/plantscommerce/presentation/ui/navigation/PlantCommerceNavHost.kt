package com.fernanortega.plantscommerce.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.fernanortega.plantscommerce.presentation.ui.PlantsCommerceState
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.navgraphs.authNavGraph

@Composable
fun PlantCommerceNavHost(
    appState: PlantsCommerceState,
    modifier: Modifier = Modifier,
    startDestination: String = Routes.Login.route
) {
    val navController = appState.navController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        authNavGraph(
            navController = navController,
            appState = appState
        )
    }
}