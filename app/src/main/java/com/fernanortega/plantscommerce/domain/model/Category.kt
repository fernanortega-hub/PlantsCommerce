package com.fernanortega.plantscommerce.domain.model

import androidx.compose.runtime.Immutable
import com.fernanortega.plantscommerce.data.local.model.CategoryEntity
import com.fernanortega.plantscommerce.data.network.model.CategoryDto

@Immutable
data class Category(
    val _id: String = "",
    val name: String = ""
) {
    fun toDto(): CategoryDto = CategoryDto(
        _id = _id,
        name = name
    )

    fun toEntity(): CategoryEntity = CategoryEntity(
        _id = _id,
        name = name
    )
}