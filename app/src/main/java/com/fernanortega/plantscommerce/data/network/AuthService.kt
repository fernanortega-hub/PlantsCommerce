package com.fernanortega.plantscommerce.data.network

import com.fernanortega.plantscommerce.data.network.model.UserDto
import com.fernanortega.plantscommerce.utils.ResultHandler
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): ResultHandler<String?>

    @POST("auth/register")
    suspend fun register(@Body user: UserDto): ResultHandler<UserDto?>
}

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)