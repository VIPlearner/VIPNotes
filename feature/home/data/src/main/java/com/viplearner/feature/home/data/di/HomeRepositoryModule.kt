package com.viplearner.feature.home.data.di

import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.feature.home.data.repository.HomeRepositoryImpl
import com.viplearner.feature.home.data.service.HomeService
import com.viplearner.feature.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(
        homeService: HomeService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): HomeRepository =
        HomeRepositoryImpl(homeService, ioDispatcher)
}