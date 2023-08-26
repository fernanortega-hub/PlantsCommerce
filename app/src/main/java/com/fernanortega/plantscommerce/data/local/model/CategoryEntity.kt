package com.fernanortega.plantscommerce.data.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fernanortega.plantscommerce.domain.model.Category

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey val _id: String = "",
    val name: String = ""
) {
    fun toDomain(): Category = Category(
        _id = _id,
        name = name
    )
}