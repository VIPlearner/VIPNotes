package com.viplearner.feature.single_note.presentation.state

import com.viplearner.feature.single_note.presentation.model.SingleNoteItem

sealed class SingleNoteScreenUiState {
    data object Loading : SingleNoteScreenUiState()
    data class Error(val errorMessage: String) : SingleNoteScreenUiState()
    data class Content(val singleNoteItem: SingleNoteItem) : SingleNoteScreenUiState()
}
