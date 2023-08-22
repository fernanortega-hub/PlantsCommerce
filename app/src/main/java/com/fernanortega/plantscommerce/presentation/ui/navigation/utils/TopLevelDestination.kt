package com.fernanortega.plantscommerce.presentation.ui.navigation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.fernanortega.plantscommerce.R

/**
 * Root destination
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    MENU(
        selectedIcon = Icons.Rounded.ShoppingBag,
        unselectedIcon = Icons.Outlined.ShoppingBag,
        iconTextId = R.string.plants,
        titleTextId = R.string.plants
    ),
    SHOPPING_CART(
        selectedIcon = Icons.Rounded.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        iconTextId = R.string.shopping_cart,
        titleTextId = R.string.shopping_cart
    ),
    PROFILE(
        selectedIcon = Icons.Rounded.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        iconTextId = R.string.profile,
        titleTextId = R.string.profile
    ),
}