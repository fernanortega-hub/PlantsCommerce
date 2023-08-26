package com.fernanortega.plantscommerce.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.fernanortega.plantscommerce.data.local.model.ProductEntity
import com.fernanortega.plantscommerce.data.network.model.ProductDto

@Stable
@Immutable
data class Product(
    val _id: String = "",
    val categories: List<Category> = emptyList(),
    val createdAt: String = "",
    val description: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    val updatedAt: String = "",
    val user: User = User()
) {
    fun toDto(): ProductDto = ProductDto(
        categories = categories.map { it.toDto() },
        createdAt = createdAt,
        description = description,
        _id = _id,
        name = name,
        price = price,
        stock = stock,
        updatedAt = updatedAt,
        user = user.toDto()
    )

    fun toEntity(): ProductEntity = ProductEntity(
        createdAt = createdAt,
        description = description,
        _id = _id,
        name = name,
        price = price,
        stock = stock,
        updatedAt = updatedAt
    )
}
