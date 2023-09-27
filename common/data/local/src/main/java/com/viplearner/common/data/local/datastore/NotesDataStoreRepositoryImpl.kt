/*
 * Copyright (c) 2022. Nomba Financial Services
 *
 * author: Victor Shoaga
 * email: victor.shoaga@nomba.com
 * github: @inventvictor
 *
 */

package com.viplearner.common.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.viplearner.common.domain.datastore.NotesDataStorePreferenceKeys
import com.viplearner.common.domain.datastore.NotesDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class NotesDataStoreRepositoryImpl @Inject constructor (
    private val prefsDataStore: DataStore<Preferences>
) : NotesDataStoreRepository {

    override suspend fun clearData() {
        prefsDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun saveUserData(state: String) {
        prefsDataStore.edit { preferences ->
            preferences[NotesDataStorePreferenceKeys.USER_DATA] = state
        }
    }

    override fun getUserData(): Flow<String> {
        return  prefsDataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[NotesDataStorePreferenceKeys.USER_DATA]?:""
            }
    }

    override suspend fun clearUserData() {
        saveUserData("")
    }

    override suspend fun getPrivateKey(): Flow<String> {
        return  prefsDataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[NotesDataStorePreferenceKeys.PRIVATE_KEY]?:""
            }
    }

    override suspend fun savePrivateKey(privateKey: String) {
        prefsDataStore.edit { preferences ->
            preferences[NotesDataStorePreferenceKeys.PRIVATE_KEY] = privateKey
        }
    }

}