package com.viplearner.feature.home.domain.repository

import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.common.domain.entity.UserData
import com.viplearner.feature.home.domain.entity.HomeError
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getListBySearchText(searchText: String): Flow <Result<List<NoteEntity>, HomeError>>
    suspend fun getList(): Flow <Result<List<NoteEntity>, HomeError>>
    suspend fun deleteNotes(uuidList: List<String>): Flow<Result<Unit, HomeError>>
    suspend fun pinNotes(uuidList: List<String>): Flow<Result<Unit, HomeError>>
    suspend fun unpinNotes(uuidList: List<String>): Flow<Result<Unit, HomeError>>
    suspend fun addNote(noteEntity: NoteEntity): Flow<Result<Unit, HomeError>>
    suspend fun loadUserData(): Flow<Result<UserData, HomeError>>
    suspend fun signOut(): Flow<Result<Unit, HomeError>>
    suspend fun signIn(email: String, password: String): Flow<Result<Unit, HomeError>>
    suspend fun signUp(email: String, password: String): Flow<Result<Unit, HomeError>>
    suspend fun syncNotes(uid: String, onlineNotes: List<NoteEntity>?): Flow<Result<Unit, HomeError>>
    suspend fun observeNotes(uid: String): Flow<Result<List<NoteEntity>, HomeError>>
    suspend fun getSyncState(): Flow<Result<Boolean, HomeError>>
}