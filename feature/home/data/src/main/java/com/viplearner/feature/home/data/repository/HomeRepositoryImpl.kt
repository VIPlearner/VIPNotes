package com.viplearner.feature.home.data.repository

import com.google.firebase.FirebaseNetworkException
import com.viplearner.common.data.local.mapper.toNoteEntity
import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.common.domain.entity.UserData
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
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getListBySearchText(searchText: String): Flow<Result<List<NoteEntity>, HomeError>> =
        homeService.getListBySearchText(searchText).flatMapConcat { list ->
            flow<Result<List<NoteEntity>, HomeError>> {
                emit(Result.Success(list.map { it.toNoteEntity() }.filter { !it.isDeleted }))
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
                emit(Result.Success(list.map { it.toNoteEntity() }.filter { !it.isDeleted }))
            }.onStart {
                emit(Result.Loading())
            }.catch {
                emit(Result.Error(HomeError.GetListError))
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun loadUserData(): Flow<Result<UserData, HomeError>> =
        homeService.loadUserData().flatMapConcat { userDataStr ->
            flow<Result<UserData, HomeError>> {
                val userData = Json.decodeFromString<UserData>(userDataStr)
                emit(Result.Success(userData))
            }.onStart {
                emit(Result.Loading())
            }.catch {
                emit(Result.Error(HomeError.LoadUserDataError))
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

    override suspend fun signOut(): Flow<Result<Unit, HomeError>> =
        flow<Result<Unit, HomeError>> {
            homeService.signOut()
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(
                when(it){
                    is FirebaseNetworkException -> Result.Error(HomeError.NetworkError)
                    else -> Result.Error(HomeError.SignOutError)
                }
            )
        }.flowOn(ioDispatcher)

    override suspend fun signIn(email: String, password: String): Flow<Result<Unit, HomeError>> {
        return flow<Result<Unit, HomeError>> {
            homeService.signIn(email, password)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(
                when(it){
                    is FirebaseNetworkException -> Result.Error(HomeError.NetworkError)
                    else -> Result.Error(HomeError.SignInError)
                }
            )
        }.flowOn(ioDispatcher)
    }

    override suspend fun signUp(email: String, password: String): Flow<Result<Unit, HomeError>> {
        return flow<Result<Unit, HomeError>> {
            homeService.signUp(email, password)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(
                when(it){
                    is FirebaseNetworkException -> Result.Error(HomeError.NetworkError)
                    else -> Result.Error(HomeError.SignUpError)
                }
            )
        }.flowOn(ioDispatcher)
    }

    override suspend fun syncNotes(uid: String, onlineNotes: List<NoteEntity>?): Flow<Result<Unit, HomeError>> =
        flow<Result<Unit, HomeError>> {
            Timber.e("What?")
            homeService.syncNotes(uid, onlineNotes)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(
                when(it){
                    is FirebaseNetworkException -> Result.Error(HomeError.NetworkError)
                    else -> Result.Error(HomeError.SyncNotesError)
                }
            )
        }.flowOn(ioDispatcher)
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun observeNotes(uid: String): Flow<Result<List<NoteEntity>, HomeError>> =
        homeService.observeNotes(uid).flatMapConcat { list ->
            flow<Result<List<NoteEntity>, HomeError>> {
                emit(Result.Success(list.filter { !it.isDeleted }))
            }.onStart {
                emit(Result.Loading())
            }.catch {
                emit(Result.Error(HomeError.GetListError))
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getSyncState(): Flow<Result<Boolean, HomeError>> =
        homeService.getSyncState().flatMapConcat { syncState ->
            flow<Result<Boolean, HomeError>> {
                emit(Result.Success(syncState))
            }.onStart {
                emit(Result.Loading())
            }.catch {
                emit(Result.Error(HomeError.LoadSyncStateError))
            }.flowOn(ioDispatcher)
        }
}
