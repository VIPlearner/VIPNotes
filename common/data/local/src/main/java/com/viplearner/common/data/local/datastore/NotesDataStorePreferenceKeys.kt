package com.viplearner.common.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object NotesDataStorePreferenceKeys {
    const val NOTES_DATASTORE_PREFERENCES = "nomba_datastore_prefs"

    val USER_DATA = stringPreferencesKey("user_data")
}