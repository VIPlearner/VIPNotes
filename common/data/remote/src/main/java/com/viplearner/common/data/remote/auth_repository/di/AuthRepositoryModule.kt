package com.viplearner.common.data.remote.auth_repository.di

import com.viplearner.common.data.remote.auth_repository.AuthRepositoryImpl
import com.viplearner.common.domain.auth_repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthRepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()
}