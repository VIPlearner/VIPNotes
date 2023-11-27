package com.viplearner.common.domain.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object NotesDataStorePreferenceKeys {
    const val NOTES_DATASTORE_PREFERENCES = "nomba_datastore_prefs"

    val USER_DATA = stringPreferencesKey("user_data")
    val PRIVATE_KEY = stringPreferencesKey("private_key")
    val SYNC_STATE = booleanPreferencesKey("sync_state")
}