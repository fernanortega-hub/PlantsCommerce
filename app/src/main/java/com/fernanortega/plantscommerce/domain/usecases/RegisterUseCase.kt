package com.fernanortega.plantscommerce.domain.usecases

import com.fernanortega.plantscommerce.data.repositories.AuthRepository
import com.fernanortega.plantscommerce.domain.model.User
import com.fernanortega.plantscommerce.utils.ResultHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        user: User
    ): ResultHandler<User?> = authRepository.register(user)
}