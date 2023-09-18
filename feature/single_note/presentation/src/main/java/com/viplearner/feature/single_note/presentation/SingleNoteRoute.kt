package com.viplearner.feature.single_note.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.viplearner.common.presentation.component.ErrorDialog
import com.viplearner.common.presentation.component.ProgressDialog
import com.viplearner.common.presentation.component.Template
import com.viplearner.common.presentation.util.rememberLocalizationManager
import com.viplearner.feature.single_note.presentation.component.NotesRichTextEditor
import com.viplearner.feature.single_note.presentation.component.SingleNoteTextField
import com.viplearner.feature.single_note.presentation.mapper.toSingleNoteItem
import com.viplearner.feature.single_note.presentation.model.SingleNoteItem
import com.viplearner.feature.single_note.presentation.state.SingleNoteScreenUiEvent
import com.viplearner.feature.single_note.presentation.state.SingleNoteScreenUiState
import com.viplearner.feature.single_note.presentation.viewmodel.SingleNoteViewModel
import com.viplearner.feature.single_note.presentation.viewmodel.singleNoteViewModelProvider
import timber.log.Timber

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

    BackPressHandler {
        viewModel.close()
        onBackClick()
    }

    SingleNoteScreen(
        onValueChanged = {
            viewModel.updateNote(it)
        },
        onBackClick = {
            viewModel.close()
            onBackClick()
        },
        singleNoteScreenUiState = singleNoteScreenUiState,
        singleNoteScreenUiEvent = singleNoteScreenUiEvent,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SingleNoteScreen(
    onValueChanged: (SingleNoteItem) -> Unit,
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
    ) {paddingValues ->
        SingleNoteContent(
            onValueChanged = onValueChanged,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            singleNoteScreenUiState = singleNoteScreenUiState
        )

    }
}

@Composable
internal fun SingleNoteContent(
    onValueChanged: (SingleNoteItem) -> Unit,
    modifier: Modifier,
    singleNoteScreenUiState: SingleNoteScreenUiState
){
    when(singleNoteScreenUiState){
        is SingleNoteScreenUiState.Content -> {
            Column(modifier = modifier,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SingleNoteTextField(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .testTag(SingleNoteTag.title),
                    placeholderText = "Title",
                    textStyle = MaterialTheme.typography.headlineMedium,
                    value = singleNoteScreenUiState.singleNoteItem.title,
                    onValueChange = {
                        onValueChanged(
                            singleNoteScreenUiState.singleNoteItem.copy(title = it)
                        )
                    }
                )
                NotesRichTextEditor(
                    modifier = Modifier
                        .testTag(SingleNoteTag.content)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    initialValue = singleNoteScreenUiState.singleNoteItem.content,
                    onValueChanged = {
                        Timber.d("New value is ${it}")
                        onValueChanged(
                            singleNoteScreenUiState.singleNoteItem.copy(content = it)
                        )
                    }
                )
            }
        }
        is SingleNoteScreenUiState.Error -> {
            ErrorDialog(errorMessage = singleNoteScreenUiState.errorMessage)
        }
        SingleNoteScreenUiState.Loading -> {
            ProgressDialog()
        }
    }
}

@Preview
@Composable
internal fun SingleNoteScreenPreview(){
    SingleNoteScreen(
        {},
        {},
        SingleNoteScreenUiState.Content(SingleNoteItem("", "")),
        SingleNoteScreenUiEvent.Idle,
        SnackbarHostState()
    )
}