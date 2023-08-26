package com.fernanortega.plantscommerce.domain.usecases

import com.fernanortega.plantscommerce.data.repositories.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(limit: Int = 10) = productRepository.getProducts(limit)
}