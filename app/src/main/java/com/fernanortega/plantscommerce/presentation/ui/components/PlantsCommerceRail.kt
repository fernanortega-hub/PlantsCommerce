package com.fernanortega.plantscommerce.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.Routes
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.TopLevelDestination
import com.fernanortega.plantscommerce.presentation.ui.theme.PlantsCommerceTheme
import com.fernanortega.plantscommerce.utils.isTopLevelDestinationInHierarchy

@Composable
fun PlantsCommerceRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = if(selected) destination.selectedIcon!! else destination.unselectedIcon!!,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = destination.iconTextId!!)
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Preview
@Composable
fun PlantsCommerceRailPreview() {
    PlantsCommerceTheme {
        PlantsCommerceRail(
            destinations = listOf(TopLevelDestination.SHOP, TopLevelDestination.PROFILE, TopLevelDestination.SHOPPING_CART),
            onNavigateToDestination = {  },
            currentDestination = NavDestination(
                Routes.Shop.route
            )
        )
    }
}