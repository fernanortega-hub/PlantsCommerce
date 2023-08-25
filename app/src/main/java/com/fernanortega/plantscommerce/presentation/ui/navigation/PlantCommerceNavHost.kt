package com.fernanortega.plantscommerce.presentation.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fernanortega.plantscommerce.presentation.ui.PlantsCommerceState
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.navgraphs.authNavGraph
import com.fernanortega.plantscommerce.presentation.ui.navigation.navgraphs.plantCommerceGraph

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
        plantCommerceGraph(
            navController = navController,
            appState = appState
        )
    }
}

