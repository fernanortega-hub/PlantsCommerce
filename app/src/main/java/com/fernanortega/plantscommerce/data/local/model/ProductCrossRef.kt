package com.fernanortega.plantscommerce.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "products_user_category_table",
    primaryKeys = [
        "product_id",
        "category_id",
        "user_id"
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["_id"],
            childColumns = ["product_id"]
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["_id"],
            childColumns = ["category_id"]
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["_id"],
            childColumns = ["user_id"]
        ),
    ],
    indices = [
        Index(value = ["product_id"]),
        Index(value = ["category_id"]),
        Index(value = ["user_id"])
    ]
)
data class ProductCrossRef(
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "category_id") val categoryId: String,
    @ColumnInfo(name = "user_id") val userId: String,
)
