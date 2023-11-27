package com.viplearner.feature.home.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Sync
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eemmez.localization.LocalizationManager
import com.viplearner.common.domain.Result
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.home.domain.usecase.DeleteNoteUseCase
import com.viplearner.feature.home.domain.usecase.GetListBySearchTextUseCase
import com.viplearner.feature.home.domain.usecase.GetListUseCase
import com.viplearner.feature.home.domain.usecase.GetSyncStateUseCase
import com.viplearner.feature.home.domain.usecase.LoadUserDataUseCase
import com.viplearner.feature.home.domain.usecase.ObserveNotesUseCase
import com.viplearner.feature.home.domain.usecase.PinNotesUseCase
import com.viplearner.feature.home.domain.usecase.SignInViaEmailAndPasswordUseCase
import com.viplearner.feature.home.domain.usecase.SignOutUseCase
import com.viplearner.feature.home.domain.usecase.SignUpViaEmailAndPasswordUseCase
import com.viplearner.feature.home.domain.usecase.SyncNotesUseCase
import com.viplearner.feature.home.domain.usecase.UnpinNotesUseCase
import com.viplearner.feature.home.presentation.component.ConfirmDialogState
import com.viplearner.feature.home.presentation.mapper.ErrorMessageMapper
import com.viplearner.feature.home.presentation.mapper.toNoteItem
import com.viplearner.feature.home.presentation.model.NoteItem
import com.viplearner.feature.home.presentation.state.HomeScreenUiEvent
import com.viplearner.feature.home.presentation.state.HomeScreenUiState
import com.viplearner.feature.home.presentation.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val deleteNotesUseCase: DeleteNoteUseCase,
    private val getListBySearchTextUseCase: GetListBySearchTextUseCase,
    private val pinNotesUseCase: PinNotesUseCase,
    private val unpinNotesUseCase: UnpinNotesUseCase,
    private val loadUserDataUseCase: LoadUserDataUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val signInViaEmailAndPasswordUseCase: SignInViaEmailAndPasswordUseCase,
    private val signUpViaEmailAndPasswordUseCase: SignUpViaEmailAndPasswordUseCase,
    private val syncNotesUseCase: SyncNotesUseCase,
    private val getSyncStateUseCase: GetSyncStateUseCase,
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val errorMessageMapper: ErrorMessageMapper,
    private val localizationManager: LocalizationManager,
) : ViewModel() {
    private val _homeScreenUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState

    private val _signInState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState.Init)
    val signInState = _signInState.asStateFlow()

    private val _homeScreenUiEvent = MutableStateFlow<HomeScreenUiEvent>(HomeScreenUiEvent.Idle)
    val homeScreenUiEvent: StateFlow<HomeScreenUiEvent> = _homeScreenUiEvent

    private val _confirmDialogState = MutableStateFlow(ConfirmDialogState(false, {}, {}, "", "", Icons.Default.AccountBox))
    val confirmDialogState = _confirmDialogState.asStateFlow()

    private val _syncState = MutableStateFlow(false)

    var observeNotesJob: Job? = null
    init {
        getList()
        loadUserData()
    }

    override fun onCleared() {
        super.onCleared()
        observeNotesJob?.cancel()
    }

    fun getList() {
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            deleteNotesUseCase.invoke(
                noteItemList.map{it.uuid}
            ).collectLatest{result ->
                when (result) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Success(
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
        viewModelScope.launch(Dispatchers.IO) {
            pinNotesUseCase.invoke(
                noteItemList.map{it.uuid}
            ).collectLatest{result ->
                when (result) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Success(
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
        viewModelScope.launch(Dispatchers.IO) {
            unpinNotesUseCase.invoke(
                noteItemList.map{it.uuid}
            ).collectLatest{result ->
                when (result) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Success(
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
        viewModelScope.launch(Dispatchers.IO) {
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

    private fun loadUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            loadUserDataUseCase.invoke().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        result.data?.let { response ->
                            _signInState.value = SignInState.SignInSuccess(response, false)
                            getSyncState(response.userId)
                        }
                    }

                    is Result.Loading -> {
                        _signInState.value = SignInState.Loading
                    }

                    is Result.Error -> {
                        _signInState.value = SignInState.Init
                    }

                    else -> {}
                }
            }
        }
    }

    fun signInViaEmail(email: String, password: String) =
        viewModelScope.launch(Dispatchers.IO){
            signInViaEmailAndPasswordUseCase.invoke(email, password).collect {
                when (it) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Success(
                            localizationManager.getString(R.string.sign_in_success)
                        )
                        openConfirmDialogForSyncNotes()
                    }

                    is Result.Loading -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Loading
                    }

                    is Result.Error -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Error(
                            errorMessageMapper.getErrorMessage(it.error)
                        )
                    }

                    else -> {}
                }
            }
        }

    fun signUpWithEmail(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            signUpViaEmailAndPasswordUseCase.invoke(email, password).collect {
                when (it) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Success(
                            localizationManager.getString(R.string.sign_up_success)
                        )
                    }

                    is Result.Loading -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Loading
                    }

                    is Result.Error -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Error(
                            errorMessageMapper.getErrorMessage(it.error)
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    fun signOut(){
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase.invoke().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Success(
                            localizationManager.getString(R.string.sign_out_success)
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

    fun activateSignIn(){
        _signInState.value = SignInState.Init
    }

    fun activateSignUp(){
        _signInState.value = SignInState.SignUp
    }

    fun selectAll(){
        viewModelScope.launch(Dispatchers.IO) {
            _homeScreenUiState.value = HomeScreenUiState.Content.NormalMode(
                list = (homeScreenUiState.value as HomeScreenUiState.Content.NormalMode).list.map {
                    it.copy(isSelected = true)
                },
                isSelectionMode = true
            )
        }
    }

    fun deselectAll(){
        viewModelScope.launch(Dispatchers.IO) {
            _homeScreenUiState.value = HomeScreenUiState.Content.NormalMode(
                list = (homeScreenUiState.value as HomeScreenUiState.Content.NormalMode).list.map {
                    it.copy(isSelected = false)
                },
                isSelectionMode = true
            )
        }
    }

    fun syncNotes(){
        Timber.d("syncNotes")
        viewModelScope.launch(Dispatchers.IO) {
            syncNotesUseCase.invoke((_signInState.value as SignInState.SignInSuccess).userData.userId).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _signInState.value = SignInState.SignInSuccess(
                            (_signInState.value as SignInState.SignInSuccess).userData,
                            false
                        )
                        Timber.d("syncNotes successfully")
                    }

                    is Result.Loading -> {
                        _signInState.value = SignInState.SignInSuccess(
                            (_signInState.value as SignInState.SignInSuccess).userData,
                            true
                        )
                        Timber.d("syncNotes loading")
                    }

                    is Result.Error -> {
                        _signInState.value = SignInState.SignInSuccess(
                            (_signInState.value as SignInState.SignInSuccess).userData,
                            false
                        )
                        _homeScreenUiEvent.value = HomeScreenUiEvent.Error(
                            errorMessageMapper.getErrorMessage(result.error)
                        )
                        Timber.d("syncNotes: ${result.error}")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun closeConfirmDialogBox(){
        _confirmDialogState.value = _confirmDialogState.value.copy(openDialog = false)
    }

    private fun openConfirmDialogForSyncNotes(){
        _confirmDialogState.value = _confirmDialogState.value.copy(
            openDialog = true,
            onDismissRequest = {
                closeConfirmDialogBox()
            },
            onConfirmation = {
                syncNotes()
                closeConfirmDialogBox()
            },
            dialogTitle = localizationManager.getString(R.string.sync_notes_dialog_title),
            dialogText = localizationManager.getString(R.string.sync_notes_dialog_text),
            icon = Icons.Default.Sync
        )
    }

    private fun observeNotes(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        observeNotesUseCase.invoke(uid).dropWhile { it is Result.Loading }.collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    syncNotesWithOnlineNotes(uid, result.data ?: emptyList())
                }
                is Result.Error -> {}

                else -> {}
            }
        }
    }

    private fun syncNotesWithOnlineNotes(uid: String, onlineNotes: List<NoteEntity>) = viewModelScope.launch(Dispatchers.IO) {
        Timber.d("WHy")
        syncNotesUseCase.invoke(uid, onlineNotes).collectLatest {
            when (it) {
                is Result.Success -> {
                    Timber.d("syncNotesWithOnlineNotes successfully")
                }

                is Result.Loading -> {
                    Timber.d("syncNotesWithOnlineNotes loading")
                }

                is Result.Error -> {
                    Timber.d("syncNotesWithOnlineNotes: ${it.error}")
                }

                else -> {}
            }
        }
    }

    private fun getSyncState(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        getSyncStateUseCase.invoke().dropWhile { it is Result.Loading }.collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    Timber.d("getSyncState: ${result.data}")
                    if(result.data == true){
                        observeNotesJob = observeNotes(uid)
                        observeNotesJob!!.start()
                    }
                    else observeNotesJob?.cancel()
                }
                is Result.Error -> {
                }
                else -> {}
            }
        }
    }
}