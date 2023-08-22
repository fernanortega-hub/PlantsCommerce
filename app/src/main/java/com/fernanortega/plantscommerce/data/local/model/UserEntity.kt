package com.fernanortega.plantscommerce.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val _id: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String? = null,
    val role: String = ""
)