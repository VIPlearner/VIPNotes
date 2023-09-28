package com.viplearner.common.data.remote.fb_database_repository.di

import com.viplearner.common.data.remote.fb_database_repository.DatabaseRepositoryImpl
import com.viplearner.common.data.remote.service.DatabaseService
import com.viplearner.common.domain.firebase_database_repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseRepositoryModule {
    @Provides
    @Singleton
    fun provideDatabaseRepository(
        databaseService: DatabaseService
    ): DatabaseRepository = DatabaseRepositoryImpl(databaseService)
}