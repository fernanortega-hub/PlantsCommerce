package com.fernanortega.plantscommerce.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PopulatedProduct(
    @Embedded val productEntity: ProductEntity,
    @Relation(
        parentColumn = "_id",
        entityColumn = "_id",
        entity = CategoryEntity::class
    )
    val categories: List<CategoryEntity>,
    @Relation(
        parentColumn = "_id",
        entityColumn = "_id",
        entity = UserEntity::class
    )
    val user: UserEntity
)
