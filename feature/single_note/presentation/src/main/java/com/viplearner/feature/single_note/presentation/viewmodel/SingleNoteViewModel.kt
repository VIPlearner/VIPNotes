package com.viplearner.feature.single_note.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.eemmez.localization.LocalizationManager
import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.single_note.domain.usecase.GetNoteUseCase
import com.viplearner.feature.single_note.domain.usecase.UpdateNoteUseCase
import com.viplearner.feature.single_note.presentation.mapper.ErrorMessageMapper
import com.viplearner.feature.single_note.presentation.mapper.toSingleNoteItem
import com.viplearner.feature.single_note.presentation.model.SingleNoteItem
import com.viplearner.feature.single_note.presentation.state.SingleNoteScreenUiEvent
import com.viplearner.feature.single_note.presentation.state.SingleNoteScreenUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SingleNoteViewModel @AssistedInject constructor(
    @Assisted("noteUUID") private val noteUUID: String,
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val errorMessageMapper: ErrorMessageMapper,
    private val localizationManager: LocalizationManager
) : ViewModel() {
    private val _singleNoteScreenUiState = MutableStateFlow<SingleNoteScreenUiState>(SingleNoteScreenUiState.Loading)
    val singleNoteScreenUiState: StateFlow<SingleNoteScreenUiState> = _singleNoteScreenUiState

    private val _singleNoteScreenUiEvent = MutableStateFlow<SingleNoteScreenUiEvent>(SingleNoteScreenUiEvent.Idle)
    val singleNoteScreenUiEvent: StateFlow<SingleNoteScreenUiEvent> = _singleNoteScreenUiEvent

    private val _isUpdatingNote = MutableStateFlow(false)

    private var noteEntity: NoteEntity = NoteEntity(
        title = "",
        content = "",
        isPinned = false,
        timeLastEdited = System.currentTimeMillis(),
    )

    init {
        if(noteUUID.isNotEmpty()){ getNote(noteUUID) }
    }

    override fun onCleared() {
        super.onCleared()
        updateNote(noteEntity.toSingleNoteItem())
    }

    private fun getNote(uuid: String) {
        viewModelScope.launch {
            getNoteUseCase.invoke(uuid) { result ->
                when (result) {
                    is Result.Success -> {
                        result.data?.let { response ->
                            noteEntity = response
                            _singleNoteScreenUiState.value =
                                SingleNoteScreenUiState.Content(response.toSingleNoteItem())
                        }
                    }
                    is Result.Loading -> {
                        _singleNoteScreenUiState.value = SingleNoteScreenUiState.Loading
                    }

                    is Result.Error -> {
                        _singleNoteScreenUiState.value = SingleNoteScreenUiState.Error(
                            errorMessage = errorMessageMapper.getErrorMessage(result.error)
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    fun updateNote(singleNoteItem: SingleNoteItem){
        _singleNoteScreenUiState.value =
            SingleNoteScreenUiState.Content(singleNoteItem)

        viewModelScope.launch {
            noteEntity.apply {
                content = singleNoteItem.content
                title = singleNoteItem.title
                timeLastEdited = System.currentTimeMillis()
            }
            updateNoteUseCase.invoke(noteEntity){result ->
                when (result) {
                    is Result.Success -> {

                    }
                    is Result.Loading -> {
                    }

                    is Result.Error -> {
                        _singleNoteScreenUiEvent.value =
                            SingleNoteScreenUiEvent.Error(errorMessageMapper.getErrorMessage(result.error))
                    }

                    else -> {}
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("noteUUID") amountString: String,
        ): SingleNoteViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            noteUUID: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return assistedFactory.create(
                    noteUUID,
                ) as T
            }
        }
    }
}