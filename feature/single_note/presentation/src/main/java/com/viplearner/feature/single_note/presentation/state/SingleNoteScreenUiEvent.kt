package com.viplearner.feature.single_note.presentation.state

sealed class SingleNoteScreenUiEvent {
    data object Idle : SingleNoteScreenUiEvent()
    data object Loading : SingleNoteScreenUiEvent()
    data class Error(val errorMessage: String) : SingleNoteScreenUiEvent()
}