package com.fernanortega.plantscommerce.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
fun PlantsCommerceBottomAppBar(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { topLevelDestination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(topLevelDestination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(topLevelDestination) },
                icon = {
                    Icon(
                        imageVector =  if(!selected) topLevelDestination.unselectedIcon!! else topLevelDestination.selectedIcon!!,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = topLevelDestination.iconTextId!!)
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}


@Preview
@Composable
fun PlantsCommerceBottomPreview() {
    PlantsCommerceTheme {
        PlantsCommerceBottomAppBar(
            destinations = listOf(TopLevelDestination.SHOP, TopLevelDestination.PROFILE, TopLevelDestination.SHOPPING_CART),
            onNavigateToDestination = {  },
            currentDestination = NavDestination(
                Routes.Shop.route
            )
        )
    }
}