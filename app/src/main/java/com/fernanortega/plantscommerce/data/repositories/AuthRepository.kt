package com.fernanortega.plantscommerce.data.repositories

import com.fernanortega.plantscommerce.data.di.IoDispatcher
import com.fernanortega.plantscommerce.data.di.RetrofitModule
import com.fernanortega.plantscommerce.data.network.AuthService
import com.fernanortega.plantscommerce.data.network.LoginRequest
import com.fernanortega.plantscommerce.domain.model.User
import com.fernanortega.plantscommerce.utils.ResultHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val authService: AuthService
) {
    suspend fun login(email: String, password: String): ResultHandler<String?> = withContext(dispatcher) {
        try {
            val response = authService.login(LoginRequest(email, password))

            if(response.isSuccessful) {
                RetrofitModule.setNewToken(response.data!!)
            }

            return@withContext authService.login(LoginRequest(email, password))
        } catch (ex: Exception) {
            return@withContext ResultHandler(
                isSuccessful = false,
                data = null,
                statusCode = 400,
                status = ex.cause?.message,
                message = ex.message
            )
        }
    }

    suspend fun register(user: User): ResultHandler<User?> = withContext(dispatcher) {
        try {
            val response = authService.register(user.toDto())
            return@withContext ResultHandler(
                isSuccessful = response.isSuccessful,
                data = response.data?.toDomain(),
                statusCode = response.statusCode,
                status = response.status,
                message = response.message
            )
        } catch (ex: Exception) {
            return@withContext ResultHandler(
                isSuccessful = false,
                data = null,
                statusCode = 400,
                status = ex.cause?.message,
                message = ex.message
            )
        }
    }
}