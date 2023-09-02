package com.viplearner.feature.home.data.service

import com.viplearner.common.data.local.database.NotesDatabase
import com.viplearner.common.data.local.dto.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeService @Inject constructor(
    private val notesDatabase: NotesDatabase
) {
    fun getList(
    ): Flow<List<Note>> =
        notesDatabase.notesDao().getAll()

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

    fun addNote(note: Note) {
        notesDatabase.notesDao().upsert(note)
    }
}