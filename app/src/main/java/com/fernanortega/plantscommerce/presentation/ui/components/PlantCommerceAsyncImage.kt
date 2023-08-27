package com.fernanortega.plantscommerce.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun PlantsCommerceAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}