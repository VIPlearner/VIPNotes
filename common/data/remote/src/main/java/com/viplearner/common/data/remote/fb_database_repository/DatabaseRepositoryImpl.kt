package com.viplearner.common.data.remote.fb_database_repository

import com.viplearner.common.data.remote.dto.Note
import com.viplearner.common.data.remote.mapper.toNote
import com.viplearner.common.data.remote.mapper.toNoteEntity
import com.viplearner.common.data.remote.service.DatabaseService
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.common.domain.firebase_database_repository.DatabaseRepository
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val databaseService: DatabaseService
): DatabaseRepository {
    override suspend fun loadAllNotes(uid: String): List<NoteEntity> {
        try{
            databaseService.loadAllNotesTask(uid).await().let { snapshot ->
                val notes = mutableListOf<NoteEntity>()
                snapshot.children.forEach { child ->
                    child.getValue(Note::class.java)?.let { note ->
                        notes.add(note.toNoteEntity())
                    }
                }
                return notes
            }
        }catch (e: Exception){
            Timber.e(e)
            throw e
        }
    }

    override suspend fun loadNote(uid: String, uuid: String): NoteEntity? {
        try{
            databaseService.loadNote(uid, uuid).await().let { snapshot ->
                snapshot.getValue(Note::class.java)?.let { note ->
                    return note.toNoteEntity()
                }?: return null
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun saveNotes(uid: String, notes: List<NoteEntity>) {
        databaseService.updateNotes(uid, notes.map { noteEntity -> noteEntity.toNote() })
    }

    override suspend fun updateNote(
        uid: String,
        note: NoteEntity
    ) {
        databaseService.updateNote(
            uid,
            note.toNote()
        )
    }

    override suspend fun observeNotes(uid: String) =
        databaseService.observeNotes(uid)
}