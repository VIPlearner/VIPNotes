package com.viplearner.notes.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eemmez.localization.LocalizationManager
import com.viplearner.common.domain.Result
import com.viplearner.notes.settings.domain.usecase.GetLoginStateUseCase
import com.viplearner.notes.settings.domain.usecase.GetSyncStateUseCase
import com.viplearner.notes.settings.domain.usecase.SetSyncStateUseCase
import com.viplearner.notes.settings.presentation.state.SettingsUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localizationManager: LocalizationManager,
    private val getSyncStateUseCase: GetSyncStateUseCase,
    private val setSyncStateUseCase: SetSyncStateUseCase,
    private val getLoginStateUseCase: GetLoginStateUseCase
): ViewModel() {
    private val _syncState = MutableStateFlow(false)
    val syncState = _syncState.asStateFlow()

    private val _loginState = MutableStateFlow(false)
    val loginState = _loginState.asStateFlow()

    private val _settingsUiEvent: MutableStateFlow<SettingsUiEvent> = MutableStateFlow(SettingsUiEvent.Init)
    val settingsUiEvent = _settingsUiEvent.asStateFlow()

    init {
        getSyncState()
        getLoginState()
    }
    fun getSyncState() = viewModelScope.launch {
        getSyncStateUseCase().collectLatest {
            when(it) {
                is Result.Success -> {
                    _syncState.value = it.data?:false
                }

                is Result.Error -> {

                }
                is Result.Loading -> {

                }
            }
        }
    }
    fun setSyncState(value: Boolean) = viewModelScope.launch {
        if(_loginState.value.not()) {
            _settingsUiEvent.value = SettingsUiEvent.Error()
            return@launch
        }
        setSyncStateUseCase(value).collectLatest {
            when(it) {
                is Result.Success -> {}
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }
    fun getLoginState() = viewModelScope.launch {
        getLoginStateUseCase().collectLatest {
            when(it) {
                is Result.Success -> {
                    _loginState.value = it.data?:false
                }
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }
}