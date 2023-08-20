package com.viplearner.feature.home.domain.usecase

import com.viplearner.common.domain.Result
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.feature.home.domain.entity.NoteEntity
import com.viplearner.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    operator fun invoke(noteEntity: NoteEntity): Flow<Result<Unit, HomeError>> =
        homeRepository.addNote(noteEntity)
}
