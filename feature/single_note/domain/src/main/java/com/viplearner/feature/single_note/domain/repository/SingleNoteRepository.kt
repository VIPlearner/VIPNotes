package com.viplearner.feature.single_note.domain.repository

import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.common.domain.Result
import com.viplearner.feature.single_note.domain.entity.SingleNoteError

interface SingleNoteRepository {
    suspend fun updateNote(noteEntity: NoteEntity, result: (Result<Unit, SingleNoteError>) -> Unit)
    suspend fun getNote(uuid: String, result: (Result<NoteEntity, SingleNoteError>) -> Unit)

}