package com.fernanortega.plantscommerce.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.fernanortega.plantscommerce.domain.model.Product
import com.fernanortega.plantscommerce.domain.model.User

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
) {
    fun toPopulatedDomain(): Product = Product(
        _id = productEntity._id,
        categories = categories.map { it.toDomain() },
        createdAt = productEntity.createdAt,
        description = productEntity.description,
        name = productEntity.name,
        price = productEntity.price,
        stock = productEntity.stock,
        updatedAt = productEntity.updatedAt,
        user = user.toDomain()
    )
}
