package com.fernanortega.plantscommerce.presentation.ui.screens.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.presentation.ui.components.FlowerShape
import com.fernanortega.plantscommerce.presentation.ui.components.PlantsCommerceAsyncImage
import com.fernanortega.plantscommerce.presentation.ui.components.PlantsCommerceTopAppBar
import com.fernanortega.plantscommerce.presentation.ui.theme.ibmPlexSansFamily
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.ProductDetailUiState
import com.fernanortega.plantscommerce.utils.toCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: ProductDetailUiState,
    isCompactScreen: Boolean,
    isInLandscape: Boolean,
    onBackAction: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Column(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PlantsCommerceTopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(R.string.product_details_label),
            onBackAction = onBackAction,
            scrollBehavior = scrollBehavior
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(
                    horizontal = if (isCompactScreen) 16.dp else 32.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            with(uiState) {
                if(!isInLandscape) {
                    PlantsCommerceAsyncImage(
                        imageUrl = product.imageUrl,
                        contentDescription = stringResource(
                            id = R.string.image_of_description,
                            product.name, product.description
                        ),
                        modifier = Modifier
                            .widthIn(max = 400.dp)
                            .clip(MaterialTheme.shapes.large),
                        contentScale = ContentScale.FillWidth
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if(isInLandscape) {
                        PlantsCommerceAsyncImage(
                            imageUrl = product.imageUrl,
                            contentDescription = stringResource(
                                id = R.string.image_of_description,
                                product.name, product.description
                            ),
                            modifier = Modifier
                                .widthIn(max = 300.dp)
                                .clip(MaterialTheme.shapes.large),
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1
                            )
                            LazyRow(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(product.categories) { category ->
                                    Text(
                                        text = category.name,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (product.stock != 0) stringResource(
                                id = R.string.in_stock_label,
                                product.stock
                            ) else stringResource(
                                id = R.string.no_stock_label
                            ),
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontFamily = ibmPlexSansFamily,
                                fontWeight = FontWeight(600),
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.25.sp,
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .requiredSize(68.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, FlowerShape)
                            .border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer, FlowerShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = product.price.toCurrency(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                Button(
                    onClick = {  },
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddShoppingCart,
                        contentDescription = null,
                        modifier = Modifier
                            .requiredSize(18.dp)
                    )
                    Spacer(modifier = Modifier.requiredSize(8.dp))
                    Text(text = "Agregar a carrito")
                }
            }
        }
    }
}