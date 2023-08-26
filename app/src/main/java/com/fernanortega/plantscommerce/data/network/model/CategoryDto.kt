package com.fernanortega.plantscommerce.data.network.model

import com.fernanortega.plantscommerce.data.local.model.CategoryEntity
import com.fernanortega.plantscommerce.domain.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("_id")
    val _id: String = "",
    @SerialName("name")
    val name: String = ""
) {
    fun toDomain(): Category = Category(
        _id = _id,
        name = name
    )

    fun toEntity(): CategoryEntity = CategoryEntity(
        _id = _id,
        name = name
    )
}
