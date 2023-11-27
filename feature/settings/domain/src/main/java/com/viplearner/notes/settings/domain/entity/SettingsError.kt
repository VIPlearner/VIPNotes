package com.viplearner.notes.settings.domain.entity

sealed class SettingsError {
    data object LoadSyncStateError : SettingsError()
    data object SetSyncStateError : SettingsError()
    data object LoadLoginStateError : SettingsError()
    data object UnknownError : SettingsError()
}