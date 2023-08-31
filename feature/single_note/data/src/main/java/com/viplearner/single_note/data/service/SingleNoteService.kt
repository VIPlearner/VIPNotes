package com.viplearner.single_note.data.service

import com.viplearner.common.data.local.database.NotesDatabase
import com.viplearner.common.data.local.dto.Note
import javax.inject.Inject

class SingleNoteService @Inject constructor(
    private val notesDatabase: NotesDatabase
) {
    fun updateNote(note: Note) {
        notesDatabase.notesDao().upsert(note)
    }

    fun getNote(uuid: String) =
        notesDatabase.notesDao().getNoteUsingUUID(uuid)

    fun createNewNote(): Note {
        val newNote = Note(
            title = "",
            content = "",
            isPinned = false,
            timeLastEdited = System.currentTimeMillis()
        )
        notesDatabase.notesDao().upsert(newNote)
        return newNote
    }

    fun deleteNote(
        uuid: String,
    ) = notesDatabase.notesDao().delete(uuid)
}