package com.fernanortega.plantscommerce.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.TopLevelDestination

@Composable
fun PlantsCommerceBottomAppBar(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {

}