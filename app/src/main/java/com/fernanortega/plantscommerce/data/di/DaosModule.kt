package com.fernanortega.plantscommerce.data.di

import com.fernanortega.plantscommerce.data.local.database.PlantsDatabase
import com.fernanortega.plantscommerce.data.local.database.dao.CategoryDao
import com.fernanortega.plantscommerce.data.local.database.dao.ProductDao
import com.fernanortega.plantscommerce.data.local.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    @Singleton
    fun provideProductDao(database: PlantsDatabase): ProductDao = database.productDao()

    @Provides
    @Singleton
    fun provideUserDao(database: PlantsDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideCategoryDao(database: PlantsDatabase): CategoryDao = database.categoryDao()
}