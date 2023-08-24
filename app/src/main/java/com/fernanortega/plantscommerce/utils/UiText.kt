package com.fernanortega.plantscommerce.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource

@Stable
sealed class UiText {
    data class UnformattedString(val value: String): UiText()
    class StringResource(@StringRes val resId: Int, vararg val args: Any): UiText()

    @Composable
    fun asString(): String {
        return when(this) {
            is UnformattedString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    @Stable
    fun asString(context: Context): String {
        return when(this) {
            is UnformattedString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}
