package com.viplearner.feature.single_note.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.viplearner.common.presentation.component.Template
import com.viplearner.feature.single_note.presentation.model.SingleNoteItem
import com.viplearner.feature.single_note.presentation.state.SingleNoteScreenUiEvent
import com.viplearner.feature.single_note.presentation.state.SingleNoteScreenUiState
import com.viplearner.feature.single_note.presentation.viewmodel.SingleNoteViewModel
import com.viplearner.feature.single_note.presentation.viewmodel.singleNoteViewModelProvider

@Composable
fun SingleNoteRoute(
    factory: SingleNoteViewModel.Factory,
    noteUUID: String,
    onBackClick: () -> Unit
){
    val viewModel: SingleNoteViewModel = singleNoteViewModelProvider(
        factory = factory,
        noteUUID = noteUUID,
    )
    val singleNoteScreenUiState by viewModel.singleNoteScreenUiState.collectAsStateWithLifecycle()
    val singleNoteScreenUiEvent by viewModel.singleNoteScreenUiEvent.collectAsStateWithLifecycle()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    SingleNoteScreen(
        onTitleChanged = { searchText ->
        },
        onContentChanged = {

        },
        onBackClick = onBackClick,
        singleNoteScreenUiState = singleNoteScreenUiState,
        singleNoteScreenUiEvent = singleNoteScreenUiEvent,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SingleNoteScreen(
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    singleNoteScreenUiState: SingleNoteScreenUiState,
    singleNoteScreenUiEvent: SingleNoteScreenUiEvent,
    snackbarHostState: SnackbarHostState
){
    Template(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Home Screen"
                        )
                    }
                },
                title = {}
            )
        }
    ) {


    }
}

@Preview
@Composable
internal fun SingleNoteScreenPreview(){
    SingleNoteScreen(
        {},
        {},
        {},
        SingleNoteScreenUiState.Content(SingleNoteItem("Help", "Help me")),
        SingleNoteScreenUiEvent.Idle,
        SnackbarHostState()
    )
}