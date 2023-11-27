package com.viplearner.notes.settings.presentation.di

import com.eemmez.localization.LocalizationManager
import com.viplearner.notes.settings.presentation.mapper.ErrorMessageMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SettingsFragmentModule {
    @Provides
    @ViewModelScoped
    fun provideErrorMessageMapper(localizationManager: LocalizationManager): ErrorMessageMapper =
        ErrorMessageMapper(localizationManager)
}