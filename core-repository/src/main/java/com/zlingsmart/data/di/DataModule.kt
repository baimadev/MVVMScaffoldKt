package com.zlingsmart.data.di

import com.zlingsmart.data.repository.HomeRepository
import com.zlingsmart.data.repository.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindHomeRepository(homeRepository: HomeRepositoryImpl):HomeRepository

}