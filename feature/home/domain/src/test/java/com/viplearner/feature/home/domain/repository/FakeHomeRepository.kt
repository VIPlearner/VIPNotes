package com.viplearner.feature.home.domain.repository

import com.viplearner.common.domain.Result
import com.viplearner.feature.home.domain.entity.GetNoteEntityListResponse
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.feature.home.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class FakeHomeRepository : HomeRepository {
    private val listData = listOf(
        NoteEntity("kedi1", "image1"),
        NoteEntity("kedi2", "image2"),
        NoteEntity("kedi3", "image3"),
        NoteEntity("kedi4", "image4"),
        NoteEntity("kedi5", "image5"),
        NoteEntity("kedi6", "image6"),
        NoteEntity("kedi7", "image7"),
        NoteEntity("kedi8", "image8"),
        NoteEntity("kedi9", "image9"),
        NoteEntity("kedi10", "image10"),
    )

    override fun getList(): Flow<Result<GetNoteEntityListResponse, HomeError>> =
        emptyFlow()

    override fun addNote(noteEntity: NoteEntity): Flow<Result<Unit, HomeError>> =
        flow<Result<Unit, HomeError>> {
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }
}