package com.viplearner.feature.home.domain.usecase

import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

class SyncNotesUseCase@Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(uid: String, onlineNotes: List<NoteEntity>? = null) =
        homeRepository.syncNotes(uid, onlineNotes)
}