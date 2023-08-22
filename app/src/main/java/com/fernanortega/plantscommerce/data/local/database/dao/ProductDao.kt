package com.fernanortega.plantscommerce.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.fernanortega.plantscommerce.data.local.model.PopulatedProduct
import com.fernanortega.plantscommerce.data.local.model.ProductCrossRef
import com.fernanortega.plantscommerce.data.local.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table")
    fun getProducts() : Flow<List<PopulatedProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("""SELECT * FROM product_table
        WHERE 
            CASE WHEN :categoryId
                THEN _id IN (
                    SELECT product_id FROM products_user_category_table
                    WHERE category_id IN (:categoryId)
                )
                ELSE 1
            END
        
    """)
    fun getProductsByCategory(categoryId: String): Flow<List<PopulatedProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCrossRefEntities(products: List<ProductCrossRef>)
}