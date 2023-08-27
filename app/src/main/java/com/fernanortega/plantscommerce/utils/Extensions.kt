package com.fernanortega.plantscommerce.utils

import android.icu.text.NumberFormat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.fernanortega.plantscommerce.presentation.ui.navigation.utils.TopLevelDestination
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.Locale

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name.lowercase(), true) ?: false
    } ?: this?.parent?.route?.contains(destination.name.lowercase()) ?: false


fun Double.toCurrency(): String = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)