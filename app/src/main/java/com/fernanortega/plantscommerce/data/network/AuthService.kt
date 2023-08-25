package com.fernanortega.plantscommerce.data.network

import com.fernanortega.plantscommerce.data.network.model.UserDto
import com.fernanortega.plantscommerce.utils.ResultHandler
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResultHandler<String?>>

    @POST("auth/register")
    suspend fun register(@Body user: UserDto): Response<ResultHandler<UserDto?>>

    @POST("auth/whoami")
    suspend fun whoAmI(): Response<ResultHandler<UserDto?>>
}

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)