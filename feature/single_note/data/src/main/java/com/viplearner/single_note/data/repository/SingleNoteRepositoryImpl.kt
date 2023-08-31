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
import timber.log.Timber

class SingleNoteRepositoryImpl(
    private val singleNoteService: SingleNoteService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): SingleNoteRepository {
    override suspend fun updateNote(
        noteEntity: NoteEntity,
    ): Flow<Result<Unit, SingleNoteError>> =
        flow<Result<Unit, SingleNoteError>> {
            singleNoteService.updateNote(noteEntity.toNote())
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(SingleNoteError.UpdateNoteError))
        }.flowOn(ioDispatcher)


    override suspend fun getNote(uuid: String) : Flow<Result<NoteEntity, SingleNoteError>> =
        flow<Result<NoteEntity, SingleNoteError>> {
            val newNote = singleNoteService.getNote(uuid)
            emit(Result.Success(newNote.toNoteEntity()))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(SingleNoteError.CreateNoteError))
        }.flowOn(ioDispatcher)


    override suspend fun createNewNote(): Flow<Result<NoteEntity, SingleNoteError>> =
        flow<Result<NoteEntity, SingleNoteError>> {
            val newNote = singleNoteService.createNewNote()
            emit(Result.Success(newNote.toNoteEntity()))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(SingleNoteError.CreateNoteError))
        }.flowOn(ioDispatcher)

    override suspend fun deleteNote(uuid: String): Flow<Result<Unit, SingleNoteError>> =
        flow<Result<Unit, SingleNoteError>> {
            singleNoteService.deleteNote(uuid)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(SingleNoteError.UpdateNoteError))
        }.flowOn(ioDispatcher)
}
