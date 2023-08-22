package com.fernanortega.plantscommerce.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey val _id: String = "",
    val name: String = "",
    val stock: Int = 0,
    val imageUrl: String? = null,
    val price: Double = 0.0,
    val description: String = "",
    val createdAt: String = "",
    val updatedAt: String = LocalDateTime.now().toString()
)