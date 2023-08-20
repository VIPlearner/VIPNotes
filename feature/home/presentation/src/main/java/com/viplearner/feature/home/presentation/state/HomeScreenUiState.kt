package com.viplearner.feature.home.presentation.state

import com.viplearner.feature.home.presentation.model.NoteItem

sealed class HomeScreenUiState {
    data class Empty(val emptyMessage: String) : HomeScreenUiState()
    data object Loading : HomeScreenUiState()
    data class Error(val errorMessage: String) : HomeScreenUiState()
    data class Content(val list: List<NoteItem>) : HomeScreenUiState()
}
