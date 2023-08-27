package com.fernanortega.plantscommerce.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.presentation.ui.components.PlantsCommerceBottomAppBar
import com.fernanortega.plantscommerce.presentation.ui.components.PlantsCommerceRail
import com.fernanortega.plantscommerce.presentation.ui.navigation.PlantCommerceNavHost
import com.fernanortega.plantscommerce.presentation.ui.rememberPlantCommerceAppState
import com.fernanortega.plantscommerce.presentation.ui.theme.PlantsCommerceTheme
import com.fernanortega.plantscommerce.utils.network.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantsCommerceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val appState = rememberPlantCommerceAppState(
                        windowSizeClass = calculateWindowSizeClass(activity = this),
                        networkMonitor = networkMonitor
                    )

                    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

                    val snackBarState = remember { SnackbarHostState() }

                    val noConnectedMessage = stringResource(id = R.string.not_connected)

                    LaunchedEffect(isOffline) {
                        if(isOffline) {
                            snackBarState.showSnackbar(
                                message = noConnectedMessage,
                                duration = SnackbarDuration.Indefinite
                            )
                        }
                    }


                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        bottomBar = {
                            if(appState.shouldShowBottomBar) {
                                PlantsCommerceBottomAppBar(
                                    destinations = appState.topLevelDestinations,
                                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                                    currentDestination = appState.currentDestination,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        },
                        snackbarHost = {
                            SnackbarHost(
                                hostState = snackBarState,
                                modifier = Modifier
                                    .widthIn(max = 460.dp)
                            )
                        }
                    ) { innerPadding ->
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                        ) {
                            if(appState.shouldShowNavRail) {
                                PlantsCommerceRail(
                                    destinations = appState.topLevelDestinations,
                                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                                    currentDestination = appState.currentDestination,
                                    modifier = Modifier
                                        .safeDrawingPadding()
                                )
                            }
                            PlantCommerceNavHost(
                                appState = appState,
                                modifier = Modifier
                                    .weight(1f),
                                startDestination = appState.startDestination
                            )
                        }
                    }

                }
            }
        }
    }


}