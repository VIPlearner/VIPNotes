package com.viplearner.feature.home.data.service

import com.viplearner.common.data.local.database.NotesDatabase
import com.viplearner.common.data.local.dto.Note
import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.common.domain.auth_repository.AuthRepository
import com.viplearner.common.domain.datastore.NotesDataStoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HomeService @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository,
    private val notesDatabase: NotesDatabase,
    private val notesDataStoreRepository: NotesDataStoreRepository
) {
    private val ioScope = CoroutineScope(ioDispatcher + Job())
    fun getList(
    ): Flow<List<Note>> =
        notesDatabase.notesDao().getAll()

    fun loadUserData(
    ): Flow<String> =
        notesDataStoreRepository.getUserData()

    fun getListBySearchText(
        searchText: String,
    ): Flow<List<Note>> =
        notesDatabase.notesDao().getNotesBySearchText(searchText)

    fun deleteNotes(
        uuidList: List<String>,
    ) = uuidList.forEach{ uuid -> notesDatabase.notesDao().delete(uuid) }

    fun pinNotes(
        uuidList: List<String>,
    ) {
        uuidList.forEach{ uuid ->
            val note = notesDatabase.notesDao().getNoteUsingUUID(uuid)
            note.isPinned = true
            notesDatabase.notesDao().upsert(note)
        }
    }

    fun unpinNotes(
        uuidList: List<String>,
    ) {
        uuidList.forEach{ uuid ->
            val note = notesDatabase.notesDao().getNoteUsingUUID(uuid)
            note.isPinned = false
            notesDatabase.notesDao().upsert(note)
        }
    }

    suspend fun signOut() {
        notesDataStoreRepository.clearUserData()
    }

    suspend fun signIn(email: String, password: String){
        val userData = authRepository.signIn(email, password)
        notesDataStoreRepository.saveUserData(Json.encodeToString(userData))
    }

    suspend fun signUp(email: String, password: String){
        val userData = authRepository.signUp(email, password)
        notesDataStoreRepository.saveUserData(Json.encodeToString(userData))
    }

    fun addNote(note: Note) {
        notesDatabase.notesDao().upsert(note)
    }
}