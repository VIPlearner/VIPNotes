package com.viplearner.feature.home.domain.repository

import com.viplearner.common.domain.Result
import com.viplearner.feature.home.domain.entity.GetNoteEntityListResponse
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.feature.home.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getList(result: (Result<GetNoteEntityListResponse, HomeError>) -> Unit)
    suspend fun addNote(noteEntity: NoteEntity): Flow<Result<Unit, HomeError>>
}