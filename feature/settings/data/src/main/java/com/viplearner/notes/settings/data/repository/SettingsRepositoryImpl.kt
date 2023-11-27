package com.viplearner.notes.settings.data.repository

import com.viplearner.common.domain.Result
import com.viplearner.notes.settings.data.service.SettingsService
import com.viplearner.notes.settings.domain.entity.SettingsError
import com.viplearner.notes.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsService: SettingsService,
    private val ioDispatcher: CoroutineDispatcher
): SettingsRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getSyncState(): Flow<Result<Boolean, SettingsError>> =
        settingsService.loadSyncState().flatMapConcat { syncState ->
            flow<Result<Boolean, SettingsError>> {
                emit(Result.Success(syncState))
            }.onStart {
                emit(Result.Loading())
            }.catch {
                emit(Result.Error(SettingsError.LoadSyncStateError))
            }.flowOn(ioDispatcher)
        }

    override suspend fun setSyncState(value: Boolean): Flow<Result<Unit, SettingsError>> =
        flow<Result<Unit, SettingsError>> {
            settingsService.setSyncState(value)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading())
        }.catch {
            emit(Result.Error(SettingsError.SetSyncStateError))
        }.flowOn(ioDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getLoginState(): Flow<Result<Boolean, SettingsError>> =
        settingsService.loadUserData().flatMapConcat { userDataStr ->
            flow<Result<Boolean, SettingsError>> {
                val loginState = userDataStr.isNotEmpty()
                emit(Result.Success(loginState))
            }.onStart {
                emit(Result.Loading())
            }.catch {
                emit(Result.Error(SettingsError.LoadLoginStateError))
            }.flowOn(ioDispatcher)
        }
}