package com.viplearner.single_note.data.service

import com.viplearner.common.data.local.database.NotesDatabase
import com.viplearner.common.data.local.dto.Note
import com.viplearner.common.data.local.mapper.toNoteEntity
import com.viplearner.common.domain.datastore.NotesDataStoreRepository
import com.viplearner.common.domain.entity.UserData
import com.viplearner.common.domain.firebase_database_repository.DatabaseRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SingleNoteService @Inject constructor(
    private val notesDatabase: NotesDatabase,
    private val notesDataStoreRepository: NotesDataStoreRepository,
    private val databaseRepository: DatabaseRepository,
) {
    suspend fun updateNote(note: Note) {
        notesDatabase.notesDao().upsert(note)
        val syncState = notesDataStoreRepository.getSyncState().firstOrNull()
        val uid = notesDataStoreRepository.getUserData().map {
            try{ Json.decodeFromString<UserData>(it).userId }
            catch(e: Exception) {null}
        }.firstOrNull()
        if(syncState == true && uid != null)
            databaseRepository.updateNote(uid, note.toNoteEntity())
    }

    fun getNote(uuid: String) =
        notesDatabase.notesDao().getNoteUsingUUID(uuid)

    fun createNewNote(): Note {
        val newNote = Note(
            title = "",
            content = "",
            isPinned = false,
            timeLastEdited = System.currentTimeMillis(),
            isDeleted = false
        )
        notesDatabase.notesDao().upsert(newNote)
        return newNote
    }

    fun deleteNote(
        uuid: String,
    ) {
        notesDatabase.notesDao().getNoteUsingUUID(uuid)
            .let { note ->
                notesDatabase.notesDao().upsert(note.copy(isDeleted = true))
            }
    }
}