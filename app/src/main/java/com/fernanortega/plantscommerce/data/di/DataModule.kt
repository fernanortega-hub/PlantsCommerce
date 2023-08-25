package com.fernanortega.plantscommerce.data.di

import com.fernanortega.plantscommerce.utils.network.NetworkMonitor
import com.fernanortega.plantscommerce.utils.network.NetworkMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: NetworkMonitorImpl
    ): NetworkMonitor
}