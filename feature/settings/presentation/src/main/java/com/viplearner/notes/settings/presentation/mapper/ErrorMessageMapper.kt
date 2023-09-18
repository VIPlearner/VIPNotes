package com.viplearner.notes.settings.presentation.mapper

import com.eemmez.localization.LocalizationManager
import com.viplearner.notes.settings.domain.entity.SettingsError
import com.viplearner.common.presentation.R as commonResources

class ErrorMessageMapper(
    private val localizationManager: LocalizationManager
) {
    fun getErrorMessage(error: SettingsError?): String =
        when (error) {
            else -> localizationManager.getString(commonResources.string.unknown_error)
        }

}