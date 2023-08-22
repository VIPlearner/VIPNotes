package com.viplearner.feature.home.presentation.mapper

import com.eemmez.localization.LocalizationManager
import com.viplearner.feature.home.domain.entity.HomeError
import com.viplearner.feature.home.presentation.R
import com.viplearner.common.presentation.R as commonResources

class ErrorMessageMapper(
    private val localizationManager: LocalizationManager
) {
    fun getErrorMessage(error: HomeError?): String =
        when (error) {
            is HomeError.GetListError -> localizationManager.getString(R.string.some_list_error)
            is HomeError.AddNoteError -> localizationManager.getString(R.string.add_note_error)
            else -> localizationManager.getString(commonResources.string.unknown_error)
        }

}