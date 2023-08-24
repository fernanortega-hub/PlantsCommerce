package com.fernanortega.plantscommerce.domain.usecases

import com.fernanortega.plantscommerce.data.repositories.AuthRepository
import com.fernanortega.plantscommerce.utils.ResultHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): ResultHandler<String?> =
        authRepository.login(email, password)
}