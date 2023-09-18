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
            is HomeError.GetListBySearchTextError -> localizationManager.getString(R.string.get_list_by_search_error)
            is HomeError.AddNoteError -> localizationManager.getString(R.string.add_note_error)
            is HomeError.DeleteNoteError -> localizationManager.getString(R.string.delete_note_error)
            is HomeError.PinNotesError -> localizationManager.getString(R.string.pin_note_error)
            is HomeError.UnpinNotesError -> localizationManager.getString(R.string.unpin_note_error)
            is HomeError.LoadUserDataError -> localizationManager.getString(R.string.load_user_data_error)
            else -> localizationManager.getString(commonResources.string.unknown_error)
        }

}