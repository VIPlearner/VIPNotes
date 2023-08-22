package com.viplearner.single_note.data.repository

import com.viplearner.common.data.local.mapper.toNote
import com.viplearner.common.data.local.mapper.toNoteEntity
import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.single_note.domain.entity.SingleNoteError
import com.viplearner.feature.single_note.domain.repository.SingleNoteRepository
import com.viplearner.single_note.data.service.SingleNoteService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class SingleNoteRepositoryImpl(
    private val singleNoteService: SingleNoteService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): SingleNoteRepository {
    override suspend fun updateNote(
        noteEntity: NoteEntity,
        result: (Result<Unit, SingleNoteError>) -> Unit
    ) {
        flow<Result<Unit, SingleNoteError>> {
            singleNoteService.updateNote(noteEntity.toNote())
            result(Result.Success(Unit))
        }.onStart {
            result(Result.Loading())
        }.catch {
            result(Result.Error(SingleNoteError.UpdateNoteError))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getNote(uuid: String, result: (Result<NoteEntity, SingleNoteError>) -> Unit) {
        flow<Result<Unit, SingleNoteError>> {
            val note = singleNoteService.getNote(uuid)
            result(Result.Success(note.toNoteEntity()))
        }.onStart {
            result(Result.Loading())
        }.catch {
            result(Result.Error(SingleNoteError.GetNoteError))
        }.flowOn(ioDispatcher)
    }
}