package com.fernanortega.plantscommerce.domain.usecases

import com.fernanortega.plantscommerce.data.repositories.ProductRepository
import com.fernanortega.plantscommerce.domain.model.Product
import com.fernanortega.plantscommerce.utils.ResultHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: String): ResultHandler<Product?> = productRepository.getProductById(productId)
}