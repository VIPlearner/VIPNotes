package com.viplearner.notes.settings.data.service

import com.viplearner.common.data.local.database.NotesDatabase
import com.viplearner.common.domain.auth_repository.AuthRepository
import com.viplearner.common.domain.datastore.NotesDataStoreRepository
import com.viplearner.common.domain.firebase_database_repository.DatabaseRepository
import javax.inject.Inject

class SettingsService @Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository,
    private val notesDatabase: NotesDatabase,
    private val notesDataStoreRepository: NotesDataStoreRepository
) {
    fun loadUserData() = notesDataStoreRepository.getUserData()

    suspend fun loadSyncState() = notesDataStoreRepository.getSyncState()
    suspend fun setSyncState(value: Boolean) {
        notesDataStoreRepository.setSyncState(value)
    }
}