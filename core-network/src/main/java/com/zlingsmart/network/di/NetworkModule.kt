package com.zlingsmart.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zlingsmart.network.Settings
import com.zlingsmart.network.interceptor.HttpRequestInterceptor
import com.zlingsmart.network.interceptor.LoggingInterceptor
import com.zlingsmart.network.interceptor.logging.LogInterceptor
import com.zlingsmart.network.service.NetworkClient
import com.zlingsmart.network.service.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providerOkHttpClient():OkHttpClient{
        val loggingInterceptor =  LoggingInterceptor(LoggingInterceptor.Logger.DEFAULT).apply { level = LoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            //.addInterceptor(loggingInterceptor)
            .addInterceptor(LogInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providerRetrofit(okHttpClient: OkHttpClient) :Retrofit{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Settings.DEBUG_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            //.addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providerService(retrofit: Retrofit):NetworkService{
        return retrofit.create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun providerNetworkClient(networkService: NetworkService):NetworkClient{
        return NetworkClient(networkService)
    }

}