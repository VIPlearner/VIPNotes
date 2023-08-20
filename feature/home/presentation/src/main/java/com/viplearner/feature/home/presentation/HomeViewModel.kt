package com.viplearner.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eemmez.localization.LocalizationManager
import com.viplearner.common.domain.Result
import com.viplearner.feature.home.domain.usecase.AddNoteUseCase
import com.viplearner.feature.home.domain.usecase.GetListUseCase
import com.viplearner.feature.home.presentation.mapper.ErrorMessageMapper
import com.viplearner.feature.home.presentation.mapper.toNoteItem
import com.viplearner.feature.home.presentation.state.HomeScreenUiEvent
import com.viplearner.feature.home.presentation.state.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val addNotesUseCase: AddNoteUseCase,
    private val errorMessageMapper: ErrorMessageMapper,
    private val localizationManager: LocalizationManager
) : ViewModel() {
    private val _homeScreenUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState

    private val _homeScreenUiEvent = MutableStateFlow<HomeScreenUiEvent>(HomeScreenUiEvent.Idle)
    val homeScreenUiEvent: StateFlow<HomeScreenUiEvent> = _homeScreenUiEvent

    init {
        getList()
    }

    fun getList() {
        viewModelScope.launch {
            getListUseCase.invoke { result ->
                    when (result) {
                        is Result.Success -> {
                            result.data?.let { response ->
                                if(response.list.isNotEmpty()){
                                    _homeScreenUiState.value =
                                        HomeScreenUiState.Content(list = response.list.map { it.toNoteItem() })
                                }
                                else{
                                    _homeScreenUiState.value =
                                        HomeScreenUiState.Empty(localizationManager.getString(R.string.create_your_first_note))
                                }
                            }
                        }

                        is Result.Loading -> {
                            _homeScreenUiState.value = HomeScreenUiState.Loading
                        }

                        is Result.Error -> {
                            _homeScreenUiState.value = HomeScreenUiState.Error(
                                errorMessage = errorMessageMapper.getErrorMessage(result.error)
                            )
                        }

                        else -> {}
                    }
                }
        }
    }

//    fun addNote(noteItem: NoteItem) {
//        viewModelScope.launch {
//            addNotesUseCase.invoke(noteItem.to)
//                .collect { result ->
//                    when (result) {
//                        is Result.Success -> {
//                            _homeScreenUiEvent.value = HomeScreenUiEvent.AddNoteSuccess(
//                                localizationManager.getString(R.string.add_favourite_success)
//                            )
//                        }
//
//                        is Result.Loading -> {
//                            _homeScreenUiEvent.value = HomeScreenUiEvent.Loading
//                        }
//
//                        is Result.Error -> {
//                            _homeScreenUiEvent.value = HomeScreenUiEvent.Error(
//                                errorMessage = errorMessageMapper.getErrorMessage(result.error)
//                            )
//                        }
//                    }
//                }
//        }
//    }
}