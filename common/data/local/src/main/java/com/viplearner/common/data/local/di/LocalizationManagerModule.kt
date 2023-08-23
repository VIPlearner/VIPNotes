package com.viplearner.common.data.local.di

import android.content.Context
import com.eemmez.localization.LocalizationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalizationManagerModule {
    @Provides
    @Singleton
    fun provideLocalizationManager(@ApplicationContext context: Context): LocalizationManager =
        LocalizationManager(context)
}
