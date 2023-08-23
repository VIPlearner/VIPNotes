package com.viplearner.feature.single_note.domain.repository

import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.single_note.domain.entity.SingleNoteError
import kotlinx.coroutines.flow.Flow

interface SingleNoteRepository {
    suspend fun updateNote(noteEntity: NoteEntity) : Flow<Result<Unit, SingleNoteError>>
    suspend fun getNote(uuid: String): Flow<Result<NoteEntity, SingleNoteError>>
    suspend fun createNewNote() : Flow<Result<NoteEntity, SingleNoteError>>

}