package com.viplearner.feature.home.domain.usecase

import com.viplearner.common.domain.Result
import com.viplearner.feature.home.domain.entity.NoteEntity
import com.viplearner.feature.home.domain.repository.FakeHomeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddToFavouritesUseCaseTest {
    private lateinit var fakeHomeRepository: FakeHomeRepository
    private lateinit var addNoteUseCase: AddNoteUseCase

    @Before
    fun setup() {
        fakeHomeRepository = FakeHomeRepository()
        addNoteUseCase = AddNoteUseCase(fakeHomeRepository)
    }

    @Test
    fun `get loading result on start`() {
        runTest {
            val result = addNoteUseCase.invoke(NoteEntity("kedi", "image1")).first()
            assert(result is Result.Loading)
        }
    }
}