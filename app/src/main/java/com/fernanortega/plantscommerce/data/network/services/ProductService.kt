package com.fernanortega.plantscommerce.data.network.services

import com.fernanortega.plantscommerce.data.network.model.ProductDto
import com.fernanortega.plantscommerce.utils.ResultHandler
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int): Response<ResultHandler<List<ProductDto>?>>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): Response<ResultHandler<ProductDto?>>
}