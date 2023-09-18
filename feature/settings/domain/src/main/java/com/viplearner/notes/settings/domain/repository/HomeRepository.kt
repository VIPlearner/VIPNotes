package com.viplearner.notes.settings.domain.repository

import com.viplearner.notes.settings.domain.entity.SettingsError
import kotlinx.coroutines.flow.Flow
import com.viplearner.common.domain.Result

interface SettingsRepository {
    suspend fun signIn(): Flow<Result<Unit, SettingsError>>
}