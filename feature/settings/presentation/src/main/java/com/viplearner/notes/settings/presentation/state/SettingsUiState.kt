package com.viplearner.notes.settings.presentation.state

sealed class SettingsUiState {
    data object Loading : SettingsUiState()
    data object Error : SettingsUiState()
    data object Success : SettingsUiState()
}