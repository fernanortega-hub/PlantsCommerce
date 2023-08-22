package com.fernanortega.plantscommerce.data.di

import android.content.Context
import androidx.room.Room
import com.fernanortega.plantscommerce.data.local.database.PlantsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun providesPlantsDatabase(
        @ApplicationContext context: Context
    ): PlantsDatabase = Room.databaseBuilder(
        context = context,
        klass = PlantsDatabase::class.java,
        name = "plants_database"
    ).build()
}