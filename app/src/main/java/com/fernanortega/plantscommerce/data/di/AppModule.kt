package com.fernanortega.plantscommerce.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val PLANTS_COMMERCE_KEY = "plants_commerce_preferences"
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context
        .getSharedPreferences(PLANTS_COMMERCE_KEY, Context.MODE_PRIVATE)

}