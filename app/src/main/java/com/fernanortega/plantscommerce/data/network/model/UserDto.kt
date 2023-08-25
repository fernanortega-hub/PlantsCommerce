package com.fernanortega.plantscommerce.data.network.model

import com.fernanortega.plantscommerce.data.local.model.UserEntity
import com.fernanortega.plantscommerce.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserDto(
    @SerialName("_id")
    val _id: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("firstName")
    val firstName: String = "",
    @SerialName("lastName")
    val lastName: String = "",
    @SerialName("password")
    val password: String? = "",
    @SerialName("role")
    val role: String? = null
) {
    fun toDomain(): User =
        User(
            _id = _id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            role = role ?: "user"
        )
    fun toEntity(): UserEntity =
        UserEntity(
            _id = _id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            role = role ?: "user",
            password = password ?: ""
        )
}
