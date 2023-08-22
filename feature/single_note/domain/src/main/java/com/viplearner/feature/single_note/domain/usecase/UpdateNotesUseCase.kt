package com.viplearner.feature.single_note.domain.usecase

import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.single_note.domain.entity.SingleNoteError
import com.viplearner.feature.single_note.domain.repository.SingleNoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val singleNoteRepository: SingleNoteRepository,
) {
    suspend operator fun invoke(noteEntity: NoteEntity, result: (Result<Unit, SingleNoteError>) -> Unit)=
        singleNoteRepository.updateNote(noteEntity, result)
}
