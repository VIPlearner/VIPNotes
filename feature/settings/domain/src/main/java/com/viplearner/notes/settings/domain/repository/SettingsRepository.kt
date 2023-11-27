package com.viplearner.notes.settings.domain.repository

import com.viplearner.common.domain.Result
import com.viplearner.notes.settings.domain.entity.SettingsError
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSyncState(): Flow<Result<Boolean, SettingsError>>
    suspend fun setSyncState(value: Boolean): Flow<Result<Unit, SettingsError>>
    suspend fun getLoginState(): Flow<Result<Boolean, SettingsError>>
}