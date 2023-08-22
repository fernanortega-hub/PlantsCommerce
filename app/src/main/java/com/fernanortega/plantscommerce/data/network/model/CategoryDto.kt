package com.fernanortega.plantscommerce.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("_id")
    val id: String = "",
    @SerialName("name")
    val name: String = ""
)
