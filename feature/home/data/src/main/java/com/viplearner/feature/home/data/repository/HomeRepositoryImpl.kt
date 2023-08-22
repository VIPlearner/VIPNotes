package com.viplearner.feature.home.data.repository

import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.common.domain.Result
import com.viplearner.common.data.local.mapper.toNoteEntity
import com.viplearner.feature.home.data.service.HomeService
import com.viplearner.feature.home.domain.entity.GetNoteEntityListResponse
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {
    override suspend fun getList(searchText: String, result: (Result<GetNoteEntityListResponse, HomeError>) -> Unit){
        homeService
            .getListBySearchText(searchText)
            .onStart {
                result(Result.Loading())
            }.catch {
                result(Result.Error(HomeError.AddNoteError))
            }.flowOn(ioDispatcher)
            .collectLatest {
                result(
                    Result.Success(
                        GetNoteEntityListResponse(
                            list = it.map { it.toNoteEntity() }
                        )
                    )
                )
            }
    }

    override suspend fun addNote(noteEntity: NoteEntity): Flow<Result<Unit, HomeError>> {
        TODO("Not yet implemented")
    }

//    override fun addNote(noteEntity: NoteEntity): Flow<Result<Unit, HomeError>> =
//        flow<Result<Unit, HomeError>> {
////            homeService.addNote(noteEntity.toNote())
//            emit(Result.Success(Unit))
//        }.onStart {
//            emit(Result.Loading())
//        }.catch {
//            emit(Result.Error(HomeError.AddNoteError))
//        }.flowOn(ioDispatcher)
}
