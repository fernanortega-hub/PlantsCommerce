package com.fernanortega.plantscommerce.utils

import kotlinx.serialization.Serializable

@Serializable
data class ResultHandler<T>(
    val isSuccessful: Boolean = false,
    val data: T?,
    val statusCode: Int = 400,
    val status: String? = "",
    val message: String? = ""
)
