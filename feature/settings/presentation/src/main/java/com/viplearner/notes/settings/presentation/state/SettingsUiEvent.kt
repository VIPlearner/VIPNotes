package com.viplearner.notes.settings.presentation.state

sealed class SettingsUiEvent {
    data object Init : SettingsUiEvent()
    data class Error(
        val message: String = "Please login to enable sync"
    ) : SettingsUiEvent()
}