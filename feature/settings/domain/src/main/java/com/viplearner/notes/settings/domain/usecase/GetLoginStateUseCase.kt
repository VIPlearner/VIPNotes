package com.viplearner.notes.settings.domain.usecase

import com.viplearner.notes.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class GetLoginStateUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() =
        settingsRepository.getLoginState()
}