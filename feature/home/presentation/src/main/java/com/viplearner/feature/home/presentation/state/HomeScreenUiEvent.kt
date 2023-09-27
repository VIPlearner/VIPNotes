package com.viplearner.feature.home.presentation.state

sealed class HomeScreenUiEvent {
    data object Idle : HomeScreenUiEvent()
    data object Loading : HomeScreenUiEvent()
    data class Error(val errorMessage: String) : HomeScreenUiEvent()
    data class Success(val successMessage: String) : HomeScreenUiEvent()
}
