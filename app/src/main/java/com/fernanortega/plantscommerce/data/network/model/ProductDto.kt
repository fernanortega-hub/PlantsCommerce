package com.fernanortega.plantscommerce.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProductDto(
    @SerialName("categories")
    val categories: List<CategoryDto> = emptyList(),
    @SerialName("createdAt")
    val createdAt: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("_id")
    val id: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("price")
    val price: Double = 0.0,
    @SerialName("stock")
    val stock: Int = 0,
    @SerialName("updatedAt")
    val updatedAt: String = "",
    @SerialName("user")
    val user: UserDto = UserDto(),
)