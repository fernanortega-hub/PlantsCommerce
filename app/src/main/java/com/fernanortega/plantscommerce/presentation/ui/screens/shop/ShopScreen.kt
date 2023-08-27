package com.fernanortega.plantscommerce.presentation.ui.screens.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.domain.model.Product
import com.fernanortega.plantscommerce.presentation.ui.components.FlowerShape
import com.fernanortega.plantscommerce.presentation.ui.theme.ibmPlexSansFamily
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.MainMenuUiState
import com.fernanortega.plantscommerce.utils.toCurrency

@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    uiState: MainMenuUiState,
    onEvent: () -> Unit,
    isCompactScreen: Boolean,
    navigateToMoreDetails: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_user, uiState.user.firstName),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(id = R.string.what_you_want_to_do_label),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        if (uiState.products.isEmpty()) {
            Text(
                text = "Sin datos"
            )
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(),
                columns = GridCells.Adaptive(300.dp),
                contentPadding = PaddingValues(horizontal = if (isCompactScreen) 16.dp else 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.products) { product ->
                    ProductCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        product = product,
                        addToCart = { },
                        moreDetails = { navigateToMoreDetails(product._id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    addToCart: () -> Unit,
    moreDetails: () -> Unit
) {
    ElevatedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .clickable { moreDetails() }
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(
                    id = R.string.image_of_description,
                    product.name,
                    product.description
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1
                            )
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                Button(onClick = addToCart) {
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

