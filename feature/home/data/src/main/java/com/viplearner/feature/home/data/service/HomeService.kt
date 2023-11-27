package com.viplearner.feature.home.data.service

import com.viplearner.common.data.local.database.NotesDatabase
import com.viplearner.common.data.local.dto.Note
import com.viplearner.common.data.local.mapper.toNote
import com.viplearner.common.data.local.mapper.toNoteEntity
import com.viplearner.common.domain.auth_repository.AuthRepository
import com.viplearner.common.domain.datastore.NotesDataStoreRepository
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.common.domain.firebase_database_repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class HomeService @Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository,
    private val notesDatabase: NotesDatabase,
    private val notesDataStoreRepository: NotesDataStoreRepository
) {
    fun getList(
    ): Flow<List<Note>> =
        notesDatabase.notesDao().getAllFlow()

    fun loadUserData(
    ): Flow<String> =
        notesDataStoreRepository.getUserData()

    fun getListBySearchText(
        searchText: String,
    ): Flow<List<Note>> =
        notesDatabase.notesDao().getNotesBySearchText(searchText)

    fun deleteNotes(
        uuidList: List<String>,
    ) = uuidList.forEach{ uuid ->
        val note = notesDatabase.notesDao().getNoteUsingUUID(uuid)
        notesDatabase.notesDao().upsert(note.copy(isDeleted = true, timeLastEdited = System.currentTimeMillis()))
    }

    fun pinNotes(
        uuidList: List<String>,
    ) {
        uuidList.forEach{ uuid ->
            val note = notesDatabase.notesDao().getNoteUsingUUID(uuid)
            note.isPinned = true
            note.timeLastEdited = System.currentTimeMillis()
            notesDatabase.notesDao().upsert(note)
        }
    }

    fun unpinNotes(
        uuidList: List<String>,
    ) {
        uuidList.forEach{ uuid ->
            val note = notesDatabase.notesDao().getNoteUsingUUID(uuid)
            note.isPinned = false
            note.timeLastEdited = System.currentTimeMillis()
            notesDatabase.notesDao().upsert(note)
        }
    }

    suspend fun signOut() {
        notesDataStoreRepository.clearData()
        notesDatabase.clearAllTables()
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

    suspend fun syncNotes(uid: String, onlineNotes: List<NoteEntity>?) {
        Timber.d("Syncing notes")
        var offlineNotes = notesDatabase.notesDao().getAll().map { note -> note.toNoteEntity() }
        var onlineNotes = onlineNotes ?: databaseRepository.loadAllNotes(uid)
        offlineNotes.forEach {
            if (it.isDeleted) {
                databaseRepository.updateNote(uid, it)
                notesDatabase.notesDao().delete(it.uuid)
                return@forEach
            }
            val onlineNote = onlineNotes.find { onlineNote -> onlineNote.uuid == it.uuid } ?: return@forEach
            if (onlineNote.timeLastEdited < it.timeLastEdited) {
                databaseRepository.updateNote(uid, it)
            } else {
                notesDatabase.notesDao().upsert(onlineNote.toNote())
            }
        }
        onlineNotes = databaseRepository.loadAllNotes(uid)
        onlineNotes.forEach { note ->
            if (note.isDeleted) {
                notesDatabase.notesDao().delete(note.uuid)
                return@forEach
            }
            if (!notesDatabase.notesDao().noteExists(note.uuid)) {
                notesDatabase.notesDao().upsert(note.toNote())
            } else {
                val offlineNote = notesDatabase.notesDao().getNoteUsingUUID(note.uuid)
                if (offlineNote.timeLastEdited < note.timeLastEdited) {
                    notesDatabase.notesDao().upsert(note.toNote())
                } else if (offlineNote.timeLastEdited > note.timeLastEdited)
                    databaseRepository.updateNote(uid, offlineNote.toNoteEntity())
            }
        }
    }

    suspend fun observeNotes(uid: String) =
        databaseRepository.observeNotes(uid)
    suspend fun getSyncState(): Flow<Boolean> = notesDataStoreRepository.getSyncState()
}
