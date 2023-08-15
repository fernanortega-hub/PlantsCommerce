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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fernanortega.plantscommerce.presentation.ui.navigation.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.authNavGraph
import com.fernanortega.plantscommerce.presentation.ui.theme.PlantsCommerceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantsCommerceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val windowSizeClass = calculateWindowSizeClass(activity = this)

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Login.route
                    ) {
                        authNavGraph(
                            navController = navController,
                            windowSizeClass = windowSizeClass
                        )

                    }
                }
            }
        }
    }
}