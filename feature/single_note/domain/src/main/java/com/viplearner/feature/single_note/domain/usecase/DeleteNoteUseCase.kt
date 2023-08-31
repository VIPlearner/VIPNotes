package com.viplearner.feature.single_note.domain.usecase

import com.viplearner.feature.single_note.domain.repository.SingleNoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val singleNoteRepository: SingleNoteRepository
) {
    suspend operator fun invoke(uuid: String)=
        singleNoteRepository.deleteNote(uuid)
}