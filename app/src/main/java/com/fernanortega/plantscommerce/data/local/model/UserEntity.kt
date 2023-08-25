package com.fernanortega.plantscommerce.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fernanortega.plantscommerce.data.network.model.UserDto
import com.fernanortega.plantscommerce.domain.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val _id: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String? = null,
    val role: String = ""
) {
    fun toDomain(): User = User(
        _id = _id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        role = role
    )

    fun toDto(): UserDto = UserDto(
        _id = _id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        password = password ?: "",
        role = role
    )
}