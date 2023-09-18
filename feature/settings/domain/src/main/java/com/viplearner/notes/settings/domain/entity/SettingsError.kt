package com.viplearner.notes.settings.domain.entity

sealed class SettingsError {
    data object UnknownError : SettingsError()
}