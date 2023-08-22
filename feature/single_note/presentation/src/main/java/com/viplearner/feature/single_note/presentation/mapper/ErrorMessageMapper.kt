package com.viplearner.feature.single_note.presentation.mapper

import com.eemmez.localization.LocalizationManager
import com.viplearner.common.presentation.R as commonResources
import com.viplearner.feature.single_note.presentation.R
import com.viplearner.feature.single_note.domain.entity.SingleNoteError

class ErrorMessageMapper(
    private val localizationManager: LocalizationManager
) {
    fun getErrorMessage(error: SingleNoteError?): String =
        when (error) {
            is SingleNoteError.GetNoteError -> localizationManager.getString(R.string.get_note_error)
            is SingleNoteError.UpdateNoteError -> localizationManager.getString(R.string.update_note_error)
            else -> localizationManager.getString(commonResources.string.unknown_error)
        }

}