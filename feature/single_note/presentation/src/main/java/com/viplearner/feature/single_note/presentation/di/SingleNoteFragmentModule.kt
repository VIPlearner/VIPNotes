package com.viplearner.feature.single_note.presentation.di

import android.content.Context
import com.eemmez.localization.LocalizationManager
import com.viplearner.feature.single_note.presentation.mapper.ErrorMessageMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SingleNoteFragmentModule {
    @Provides
    @ViewModelScoped
    fun provideLocalizationManager(@ApplicationContext context: Context): LocalizationManager =
        LocalizationManager(context)

    @Provides
    @ViewModelScoped
    fun provideErrorMessageMapper(localizationManager: LocalizationManager): ErrorMessageMapper =
        ErrorMessageMapper(localizationManager)
}