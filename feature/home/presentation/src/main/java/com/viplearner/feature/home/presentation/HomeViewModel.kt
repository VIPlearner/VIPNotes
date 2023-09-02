package com.viplearner.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eemmez.localization.LocalizationManager
import com.viplearner.common.domain.Result
import com.viplearner.feature.home.domain.usecase.DeleteNoteUseCase
import com.viplearner.feature.home.domain.usecase.GetListBySearchTextUseCase
import com.viplearner.feature.home.domain.usecase.GetListUseCase
import com.viplearner.feature.home.domain.usecase.PinNotesUseCase
import com.viplearner.feature.home.domain.usecase.UnpinNotesUseCase
import com.viplearner.feature.home.presentation.mapper.ErrorMessageMapper
import com.viplearner.feature.home.presentation.mapper.toNoteItem
import com.viplearner.feature.home.presentation.model.NoteItem
import com.viplearner.feature.home.presentation.state.HomeScreenUiEvent
import com.viplearner.feature.home.presentation.state.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val deleteNotesUseCase: DeleteNoteUseCase,
    private val getListBySearchTextUseCase: GetListBySearchTextUseCase,
    private val pinNotesUseCase: PinNotesUseCase,
    private val unpinNotesUseCase: UnpinNotesUseCase,
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
            getListUseCase.invoke().collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            result.data?.let { response ->
                                if(response.isNotEmpty()){
                                    _homeScreenUiState.value =
                                        HomeScreenUiState.Content.NormalMode(
                                            list = response.map { it.toNoteItem() }.sortedBy { !it.isPinned },
                                            isSelectionMode = false
                                        )
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

    fun getListBySearchText(searchText: String) {
        viewModelScope.launch {
            getListBySearchTextUseCase.invoke(searchText).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        result.data?.let { response ->
                            if(response.isNotEmpty()){
                                _homeScreenUiState.value =
                                    HomeScreenUiState.Content.SearchMode(response.map { it.toNoteItem() }.sortedBy { !it.isPinned })
                            }
                            else{
                                _homeScreenUiState.value =
                                    HomeScreenUiState.NoNoteFound(localizationManager.getString(R.string.no_note_found))
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

    fun deleteNotes(noteItemList: List<NoteItem>) {
        viewModelScope.launch {
            deleteNotesUseCase.invoke(
                noteItemList.map{it.uuid}
            ).collectLatest{result ->
                when (result) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.DeleteNoteSuccess(
                            localizationManager.getString(R.string.note_deleted)
                        )
                    }

                    is Result.Loading -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Loading
                    }

                    is Result.Error -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Error(
                            errorMessageMapper.getErrorMessage(result.error)
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun pinNotes(noteItemList: List<NoteItem>) {
        viewModelScope.launch {
            pinNotesUseCase.invoke(
                noteItemList.map{it.uuid}
            ).collectLatest{result ->
                when (result) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.DeleteNoteSuccess(
                            localizationManager.getString(R.string.note_pinned)
                        )
                    }

                    is Result.Loading -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Loading
                    }

                    is Result.Error -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Error(
                            errorMessageMapper.getErrorMessage(result.error)
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun unpinNotes(noteItemList: List<NoteItem>) {
        viewModelScope.launch {
            unpinNotesUseCase.invoke(
                noteItemList.map{it.uuid}
            ).collectLatest{result ->
                when (result) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.DeleteNoteSuccess(
                            localizationManager.getString(R.string.note_unpinned)
                        )
                    }

                    is Result.Loading -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Loading
                    }

                    is Result.Error -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Error(
                            errorMessageMapper.getErrorMessage(result.error)
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun selectNote(noteItem: NoteItem){
        viewModelScope.launch {
            _homeScreenUiState.value = HomeScreenUiState.Content.NormalMode(
                list = (homeScreenUiState.value as HomeScreenUiState.Content.NormalMode).list.map {
                    if(it.uuid == noteItem.uuid){
                        it.copy(isSelected = !it.isSelected)
                    }
                    else{
                        it
                    }
                },
                isSelectionMode = true
            )
        }
    }

    fun selectAll(){
        viewModelScope.launch {
            _homeScreenUiState.value = HomeScreenUiState.Content.NormalMode(
                list = (homeScreenUiState.value as HomeScreenUiState.Content.NormalMode).list.map {
                    it.copy(isSelected = true)
                },
                isSelectionMode = true
            )
        }
    }

    fun deselectAll(){
        viewModelScope.launch {
            _homeScreenUiState.value = HomeScreenUiState.Content.NormalMode(
                list = (homeScreenUiState.value as HomeScreenUiState.Content.NormalMode).list.map {
                    it.copy(isSelected = false)
                },
                isSelectionMode = true
            )
        }
    }

}