/*
 * Copyright (c) 2022. Nomba Financial Services
 *
 * author: Victor Shoaga
 * email: victor.shoaga@nomba.com
 * github: @inventvictor
 *
 */

package com.viplearner.common.data.local.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.viplearner.common.data.local.datastore.NotesDataStorePreferenceKeys
import com.viplearner.common.data.local.datastore.NotesDataStoreRepository
import com.viplearner.common.data.local.datastore.NotesDataStoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.prefsDataStore by preferencesDataStore(
    name = NotesDataStorePreferenceKeys.NOTES_DATASTORE_PREFERENCES
)

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesDataStoreModule {

    @Binds
    @Singleton
    abstract fun bindNotesDataStoreRepository(
        notesDataStoreRepositoryImpl: NotesDataStoreRepositoryImpl
    ) : NotesDataStoreRepository

    companion object {
        @Provides
        @Singleton
        fun providesPreferencesDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> =
            context.prefsDataStore
    }

}
