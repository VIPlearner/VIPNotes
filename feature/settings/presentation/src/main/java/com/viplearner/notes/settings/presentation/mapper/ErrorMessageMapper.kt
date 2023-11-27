package com.viplearner.notes.settings.presentation.mapper

import com.eemmez.localization.LocalizationManager
import com.viplearner.feature.settings.presentation.R
import com.viplearner.notes.settings.domain.entity.SettingsError
import com.viplearner.common.presentation.R as commonResources

class ErrorMessageMapper(
    private val localizationManager: LocalizationManager
) {
    fun getErrorMessage(error: SettingsError?): String =
        when (error) {
            SettingsError.LoadSyncStateError -> localizationManager.getString(R.string.load_sync_state_error)
            SettingsError.SetSyncStateError -> localizationManager.getString(R.string.set_sync_state_error)
            SettingsError.LoadLoginStateError -> localizationManager.getString(R.string.load_login_state_error)
            else -> localizationManager.getString(commonResources.string.unknown_error)
        }

}