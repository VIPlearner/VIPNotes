package com.viplearner.feature.home.domain.repository

import com.viplearner.common.domain.Result
import com.viplearner.feature.home.domain.entity.GetNoteEntityListResponse
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.common.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getListBySearchText(searchText: String): Flow <Result<List<NoteEntity>, HomeError>>
    suspend fun getList(): Flow <Result<List<NoteEntity>, HomeError>>
    suspend fun deleteNotes(uuidList: List<String>): Flow<Result<Unit, HomeError>>
    suspend fun addNote(noteEntity: NoteEntity): Flow<Result<Unit, HomeError>>
}