package com.viplearner.common.domain.firebase_database_repository

import com.viplearner.common.domain.entity.NoteEntity


interface DatabaseRepository {
    suspend fun loadAllNotes(uid: String): List<NoteEntity>
    suspend fun loadNote(uid: String, uuid: String): NoteEntity?
    suspend fun saveNotes(uid: String, notes : List<NoteEntity>)
    suspend fun updateNote(uid: String, note: NoteEntity)
}