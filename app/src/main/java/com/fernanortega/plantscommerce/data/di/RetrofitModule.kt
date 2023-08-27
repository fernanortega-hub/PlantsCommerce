package com.fernanortega.plantscommerce.data.di

import com.fernanortega.plantscommerce.data.network.services.AuthService
import com.fernanortega.plantscommerce.data.network.services.ProductService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.Duration
import javax.inject.Singleton

private const val BASE_URL = "http://10.0.2.2:50829/api/v1/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val interceptorLogging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private var token = ""
    fun setNewToken(newToken: String) {
        token = newToken
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                )
            }
            .addInterceptor(interceptorLogging)
            .connectTimeout(Duration.ofSeconds(30)).readTimeout(Duration.ofSeconds(30)).build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson().newBuilder().create()

    @Provides
    @Singleton
    fun providesRetrofit(
        httpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .build()

    @Provides
    @Singleton
    fun providesAuthService(
        retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesProductService(
        retrofit: Retrofit
    ): ProductService = retrofit.create(ProductService::class.java)
}