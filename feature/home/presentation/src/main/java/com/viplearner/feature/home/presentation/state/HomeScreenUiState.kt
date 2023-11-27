package com.viplearner.feature.home.presentation.state

import com.viplearner.feature.home.presentation.model.NoteItem

sealed class HomeScreenUiState {
    data class Empty(val emptyMessage: String) : HomeScreenUiState()
    data object Loading : HomeScreenUiState()
    data class Error(val errorMessage: String) : HomeScreenUiState()
    sealed class Content(
        open val list: List<NoteItem>
    ) : HomeScreenUiState() {
        data class SearchMode(override val list: List<NoteItem>) : Content(list)
        data class NormalMode(
            override val list: List<NoteItem>,
            val isSelectionMode: Boolean
        ): Content(list){
            fun isAllSelected(): Boolean {
                return isSelectionMode && list.all { it.isSelected }
            }
        }
    }
    data class NoNoteFound(val noNoteFoundMessage: String) : HomeScreenUiState()

}
