package com.viplearner.feature.single_note.domain.usecase

import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.single_note.domain.entity.SingleNoteError
import com.viplearner.feature.single_note.domain.repository.SingleNoteRepository
import javax.inject.Inject
class GetNoteUseCase @Inject constructor(
    private val singleNoteRepository: SingleNoteRepository,
) {
    suspend operator fun invoke(uuid: String)=
        singleNoteRepository.getNote(uuid)
}
