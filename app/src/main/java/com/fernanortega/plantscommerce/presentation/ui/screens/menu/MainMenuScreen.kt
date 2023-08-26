package com.fernanortega.plantscommerce.presentation.ui.screens.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.MainMenuUiState

@Composable
fun MainMenuScreen(
    uiState: MainMenuUiState,
    onEvent: () -> Unit,
    isCompactScreen: Boolean,
    isInLandscape: Boolean
) {
    Column {
        Text(
            text = uiState.user.toString()
        )

        if (uiState.products.isEmpty()) {
            Text(
                text = "Sin datos"
            )
        } else {
            LazyColumn {
                items(uiState.products) {
                    Text(
                        text = it.toString(),
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}