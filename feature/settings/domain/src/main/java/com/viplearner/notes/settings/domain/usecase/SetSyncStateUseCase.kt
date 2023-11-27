package com.viplearner.notes.settings.domain.usecase

import com.viplearner.notes.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SetSyncStateUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: Boolean) =
        settingsRepository.setSyncState(value)
}