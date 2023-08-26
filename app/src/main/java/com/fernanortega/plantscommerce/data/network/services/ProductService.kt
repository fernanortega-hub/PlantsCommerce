package com.fernanortega.plantscommerce.data.network.services

import com.fernanortega.plantscommerce.data.network.model.ProductDto
import com.fernanortega.plantscommerce.utils.ResultHandler
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("products?limit=10")
    suspend fun getProducts(): Response<ResultHandler<List<ProductDto>>>
}