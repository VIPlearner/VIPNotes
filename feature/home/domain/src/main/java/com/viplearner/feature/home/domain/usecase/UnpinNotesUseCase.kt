package com.viplearner.feature.home.domain.usecase

import com.viplearner.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

class UnpinNotesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(uuidList: List<String>) =
        homeRepository.unpinNotes(uuidList)
}