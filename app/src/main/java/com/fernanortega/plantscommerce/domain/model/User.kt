package com.fernanortega.plantscommerce.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.fernanortega.plantscommerce.data.local.model.UserEntity
import com.fernanortega.plantscommerce.data.network.model.UserDto

@Stable
@Immutable
data class User(
    val _id: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val role: String = ""
) {
    fun toDto(): UserDto =
        UserDto(
            email = email,
            firstName = firstName,
            lastName = lastName,
            password = password,
            role = role
        )

    fun toEntity(): UserEntity =
        UserEntity(
            _id = _id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            password = password,
            role = role
        )
}
