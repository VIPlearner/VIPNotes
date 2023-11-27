package com.viplearner.notes.settings.data.di

import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.notes.settings.data.repository.SettingsRepositoryImpl
import com.viplearner.notes.settings.data.service.SettingsService
import com.viplearner.notes.settings.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsRepositoryModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsService: SettingsService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SettingsRepository =
        SettingsRepositoryImpl(settingsService, ioDispatcher)
}