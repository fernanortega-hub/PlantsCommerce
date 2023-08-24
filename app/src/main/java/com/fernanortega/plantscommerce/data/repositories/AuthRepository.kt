package com.fernanortega.plantscommerce.data.repositories

import com.fernanortega.plantscommerce.data.di.IoDispatcher
import com.fernanortega.plantscommerce.data.network.AuthService
import com.fernanortega.plantscommerce.data.network.LoginRequest
import com.fernanortega.plantscommerce.domain.model.User
import com.fernanortega.plantscommerce.utils.ResultHandler
import com.fernanortega.plantscommerce.utils.fromJson
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val authService: AuthService
) {
    suspend fun login(email: String, password: String): ResultHandler<String?> = withContext(dispatcher) {
        return@withContext try {
            val response = authService.login(LoginRequest(email, password))

            if(!response.isSuccessful) {
                Gson().fromJson<ResultHandler<String?>>(response.errorBody()!!.string())
            } else {
                ResultHandler(
                    isSuccessful = response.body()!!.isSuccessful,
                    data = response.body()!!.data,
                    statusCode = response.body()!!.statusCode,
                    status = response.body()!!.status,
                    message = response.body()!!.message
                )
            }
        } catch (ex: Exception) {
            ResultHandler(
                isSuccessful = false,
                data = null,
                statusCode = 400,
                status = ex.cause?.message ?: "Bad request",
                message = ex.message ?: "Bad request"
            )
        }
    }

    suspend fun register(user: User): ResultHandler<User?> = withContext(dispatcher) {
        return@withContext try {
            val response = authService.register(user.toDto())
            if(!response.isSuccessful) {
                Gson().fromJson<ResultHandler<User?>>(response.errorBody()!!.string())
            } else {
                ResultHandler(
                    isSuccessful = response.isSuccessful,
                    data = response.body()!!.data?.toDomain(),
                    statusCode = response.body()!!.statusCode,
                    status = response.body()!!.status,
                    message = response.body()!!.message
                )
            }
        } catch (ex: Exception) {
             ResultHandler(
                isSuccessful = false,
                data = null,
                statusCode = 400,
                status = ex.cause?.message ?: "Bad request",
                message = ex.message ?: "Bad request"
            )
        }
    }
}