package com.viplearner.feature.home.data.repository

import com.viplearner.common.data.local.mapper.toNoteEntity
import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.home.data.service.HomeService
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getListBySearchText(searchText: String): Flow<Result<List<NoteEntity>, HomeError>> =
        homeService.getListBySearchText(searchText).flatMapConcat { list ->
            flow<Result<List<NoteEntity>, HomeError>> {
                emit(Result.Success(list.map { it.toNoteEntity() }))
            }.onStart {
                emit(Result.Loading())
            }.catch {
                emit(Result.Error(HomeError.GetListBySearchTextError))
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getList(): Flow<Result<List<NoteEntity>, HomeError>> =
        homeService.getList().flatMapConcat { list ->
            flow<Result<List<NoteEntity>, HomeError>> {
                emit(Result.Success(list.map { it.toNoteEntity() }))
            }.onStart {
                emit(Result.Loading())
            }.catch {
                emit(Result.Error(HomeError.GetListError))
            }
        }

    override suspend fun deleteNotes(uuidList: List<String>): Flow<Result<Unit, HomeError>> =
        flow<Result<Unit, HomeError>> {
            homeService.deleteNotes(uuidList)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(HomeError.DeleteNoteError))
        }.flowOn(ioDispatcher)

    override suspend fun pinNotes(uuidList: List<String>): Flow<Result<Unit, HomeError>> =
        flow<Result<Unit, HomeError>> {
            homeService.pinNotes(uuidList)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(HomeError.PinNotesError))
        }.flowOn(ioDispatcher)

    override suspend fun unpinNotes(uuidList: List<String>): Flow<Result<Unit, HomeError>> =
        flow<Result<Unit, HomeError>> {
            homeService.unpinNotes(uuidList)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(HomeError.UnpinNotesError))
        }.flowOn(ioDispatcher)

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
