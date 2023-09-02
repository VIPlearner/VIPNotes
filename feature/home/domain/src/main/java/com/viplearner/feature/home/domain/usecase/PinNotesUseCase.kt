package com.viplearner.feature.home.domain.usecase

import com.viplearner.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

class PinNotesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(uuidList: List<String>) =
        homeRepository.pinNotes(uuidList)
}