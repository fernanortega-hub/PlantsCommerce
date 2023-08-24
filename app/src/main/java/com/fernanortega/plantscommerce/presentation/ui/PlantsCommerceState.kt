package com.fernanortega.plantscommerce.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.fernanortega.plantscommerce.data.di.RetrofitModule
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.TopLevelDestination
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.navigateToLogin
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.navigateToMenu
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.navigateToProfile
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.navigateToRegister
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.navigateToShoppingCart
import com.fernanortega.plantscommerce.utils.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberPlantCommerceAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    configuration: Configuration = LocalConfiguration.current,
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) : PlantsCommerceState {
    return remember(
        navController,
        windowSizeClass,
        coroutineScope,
        networkMonitor,
        configuration
    ) {
        PlantsCommerceState(
            navController = navController,
            windowSizeClass = windowSizeClass,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
            configuration = configuration,
            context = context
        )
    }
}

/**
 * This class is responsible for global state of the app between screens and navhosts
 */
@Stable
class PlantsCommerceState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
    val coroutineScope: CoroutineScope,
    val configuration: Configuration,
    val context: Context,
    networkMonitor: NetworkMonitor
) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("plants_commerce_preferences", Context.MODE_PRIVATE)
    }

    fun setToken(token: String) {
        sharedPreferences.apply {
            edit().putString("token", token).apply()
        }.also {
            val savedToken = it.getString("token", null)
            if(savedToken != null) {
                RetrofitModule.setNewToken(token)
            }
        }
    }

    private val isUserLogin: Boolean
        get() = sharedPreferences.contains("token")

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when(currentDestination?.route) {
            Routes.Menu.route -> TopLevelDestination.MENU
            Routes.ShoppingCart.route -> TopLevelDestination.SHOPPING_CART
            Routes.Profile.route -> TopLevelDestination.PROFILE
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = isUserLogin && windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = isUserLogin && !shouldShowBottomBar

    val isOffline: StateFlow<Boolean> = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val isInLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                if(topLevelDestination == TopLevelDestination.LOGIN) {
                    inclusive = true
                }
            }

            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true

            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when(topLevelDestination) {
            TopLevelDestination.MENU -> navController.navigateToMenu(topLevelNavOptions)
            TopLevelDestination.SHOPPING_CART -> navController.navigateToShoppingCart(topLevelNavOptions)
            TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
            TopLevelDestination.LOGIN -> navController.navigateToLogin(topLevelNavOptions)
            TopLevelDestination.REGISTER -> navController.navigateToRegister(topLevelNavOptions)
        }
    }
}