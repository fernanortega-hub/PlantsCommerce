package com.fernanortega.plantscommerce.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.fernanortega.plantscommerce.presentation.ui.navigation.PlantCommerceNavHost
import com.fernanortega.plantscommerce.presentation.ui.rememberPlantCommerceAppState
import com.fernanortega.plantscommerce.presentation.ui.theme.PlantsCommerceTheme
import com.fernanortega.plantscommerce.utils.network.NetworkMonitorImpl
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                        networkMonitor = NetworkMonitorImpl(this)
                    )

                    PlantCommerceNavHost(
                        modifier = Modifier
                            .fillMaxSize(),
                        appState = appState
                    )
                }
            }
        }
    }
}