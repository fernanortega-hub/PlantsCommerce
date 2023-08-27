package com.fernanortega.plantscommerce.data.network.model

import com.fernanortega.plantscommerce.data.local.model.ProductCrossRef
import com.fernanortega.plantscommerce.data.local.model.ProductEntity
import com.fernanortega.plantscommerce.domain.model.Product
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
    val _id: String = "",
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
    @SerialName("imageUrl")
    val imageUrl: String = ""
) {
    fun toCrossReferences(): List<ProductCrossRef> = categories.map { category ->
        ProductCrossRef(
            productId = _id,
            categoryId = category._id,
            userId = user._id
        )
    }

    fun toDomain(): Product = Product(
        _id = _id,
        categories = categories.map { it.toDomain() },
        createdAt = createdAt,
        description = description,
        name = name,
        price = price,
        stock = stock,
        updatedAt = updatedAt,
        user = user.toDomain(),
        imageUrl = imageUrl
    )

    fun toEntity(): ProductEntity = ProductEntity(
        _id = _id,
        name = name,
        stock = stock,
        imageUrl = imageUrl,
        price = price,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}