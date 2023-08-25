package com.fernanortega.plantscommerce.presentation.ui.screens.menu

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.MainMenuUiState

@Composable
fun MainMenuScreen(
    uiState: MainMenuUiState,
    onEvent: () -> Unit,
    isCompactScreen: Boolean,
    isInLandscape: Boolean
) {
    Text(
        text = uiState.user.toString()
    )
}