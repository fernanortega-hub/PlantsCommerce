package com.fernanortega.plantscommerce.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fernanortega.plantscommerce.data.local.database.dao.CategoryDao
import com.fernanortega.plantscommerce.data.local.database.dao.ProductDao
import com.fernanortega.plantscommerce.data.local.database.dao.UserDao
import com.fernanortega.plantscommerce.data.local.model.CategoryEntity
import com.fernanortega.plantscommerce.data.local.model.ProductCrossRef
import com.fernanortega.plantscommerce.data.local.model.ProductEntity
import com.fernanortega.plantscommerce.data.local.model.UserEntity

@Database(
    entities = [
        ProductEntity::class,
        UserEntity::class,
        CategoryEntity::class,
        ProductCrossRef::class
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class PlantsDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao
}