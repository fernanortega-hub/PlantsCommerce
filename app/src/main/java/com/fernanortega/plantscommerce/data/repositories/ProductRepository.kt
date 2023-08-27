package com.fernanortega.plantscommerce.data.repositories

import com.fernanortega.plantscommerce.data.di.IoDispatcher
import com.fernanortega.plantscommerce.data.local.database.dao.CategoryDao
import com.fernanortega.plantscommerce.data.local.database.dao.ProductDao
import com.fernanortega.plantscommerce.data.local.database.dao.UserDao
import com.fernanortega.plantscommerce.data.network.model.ProductDto
import com.fernanortega.plantscommerce.data.network.services.ProductService
import com.fernanortega.plantscommerce.domain.model.Product
import com.fernanortega.plantscommerce.utils.ResultHandler
import com.fernanortega.plantscommerce.utils.fromJson
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val productDao: ProductDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userDao: UserDao,
    private val categoryDao: CategoryDao
) {
    suspend fun getProducts(limit: Int = 10): ResultHandler<List<Product>?> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = productService.getProducts(limit)

                if (response.isSuccessful) {
                    val body = response.body()!!
                    insertProduct(body.data!!)
                    ResultHandler(
                        isSuccessful = true,
                        data = body.data.map { it.toDomain() },
                        statusCode = 200,
                        status = "Success",
                        message = "Products founded"
                    )
                } else {
                    val error = Gson().fromJson<ResultHandler<List<ProductDto>?>>(response.errorBody()!!.string())

                    ResultHandler(
                        isSuccessful = false,
                        data = null,
                        statusCode = error.statusCode,
                        status = error.status,
                        message = error.message
                    )
                }
            } catch (ex: Exception) {
                ResultHandler(
                    isSuccessful = false,
                    data = null,
                    statusCode = 500,
                    status = ex.message ?: "",
                    message = ex.cause?.message ?: ""
                )
            }
        }

    private suspend fun insertProduct(products: List<ProductDto>) = withContext(ioDispatcher) {
        products.forEach { productDto ->
            productDto.categories.forEach { categoryDto ->
                categoryDao.insertCategory(categoryDto.toEntity())
            }
            userDao.insertUser(productDto.user.toEntity())

            productDao.insertProduct(productDto.toEntity())

            productDao.insertProductCrossRefEntities(
                productDto.toCrossReferences()
            )
        }
    }

    suspend fun getProductById(productId: String): ResultHandler<Product?> = withContext(ioDispatcher) {
        return@withContext  try {
            val response = productService.getProductById(productId)

            if(!response.isSuccessful) {
                val errorRes = Gson().fromJson<ResultHandler<ProductDto?>>(response.errorBody()!!.string())

                ResultHandler(
                    isSuccessful = false,
                    data = null,
                    statusCode = errorRes.statusCode,
                    status = errorRes.status,
                    message = errorRes.message
                )
            } else {
                val result = response.body()!!
                ResultHandler(
                    isSuccessful = true,
                    data = result.data!!.toDomain(),
                    statusCode = result.statusCode,
                    status = result.status,
                    message = result.message
                )
            }
        } catch (ex: Exception) {
            ResultHandler(
                isSuccessful = false,
                data = null,
                statusCode = 500,
                status = ex.cause?.localizedMessage ?: "",
                message = ex.cause?.message ?: ""
            )
        }
    }
}