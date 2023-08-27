package com.fernanortega.plantscommerce.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.presentation.ui.theme.PlantsCommerceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantsCommerceTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    titleIcon: ImageVector? = null,
    onBackAction: () -> Unit,
    actions: (@Composable RowScope.() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
) {
    LargeTopAppBar(
        modifier = modifier,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title
                )

                if(titleIcon != null) {
                    Icon(
                        imageVector = titleIcon,
                        contentDescription = null
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackAction
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_to_previous_screen_label)
                )
            }
        },
        actions = {
            if(actions != null) {
                actions()
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PlantCommerceTopAppBarPreview() {
    PlantsCommerceTheme {
        PlantsCommerceTopAppBar(
            title = "Lorem", onBackAction = { /*TODO*/ }
        )
    }
}