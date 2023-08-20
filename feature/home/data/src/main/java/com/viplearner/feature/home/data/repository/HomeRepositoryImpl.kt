package com.viplearner.feature.home.data.repository

import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.common.domain.Result
import com.viplearner.feature.home.data.mapper.toNoteEntity
import com.viplearner.feature.home.data.service.HomeService
import com.viplearner.feature.home.domain.entity.GetNoteEntityListResponse
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.feature.home.domain.entity.NoteEntity
import com.viplearner.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {
    override fun getList(): Flow<Result<GetNoteEntityListResponse, HomeError>> {
        return flow<Result<GetNoteEntityListResponse, HomeError>> {
            homeService.getList().collectLatest {noteList ->
                emit(
                    Result.Success(GetNoteEntityListResponse(noteList.map { it.toNoteEntity() }))
                )
            }
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(HomeError.UnknownError))
        }.flowOn(ioDispatcher)
    }

    override fun addNote(noteEntity: NoteEntity): Flow<Result<Unit, HomeError>> =
        flow<Result<Unit, HomeError>> {
//            homeService.addNote(noteEntity.toNote())
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(HomeError.AddNoteError))
        }.flowOn(ioDispatcher)
}
