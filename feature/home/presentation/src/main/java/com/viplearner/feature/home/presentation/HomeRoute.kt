package com.viplearner.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eemmez.localization.LocalizationManager
import com.viplearner.common.presentation.component.ErrorDialog
import com.viplearner.common.presentation.component.ProgressDialog
import com.viplearner.feature.home.presentation.component.EmptyListView
import com.viplearner.feature.home.presentation.component.List
import com.viplearner.feature.home.presentation.component.NotesFloatingActionButton
import com.viplearner.common.presentation.component.Template
import com.viplearner.common.presentation.util.rememberLocalizationManager
import com.viplearner.feature.home.presentation.component.HomeBottomBar
import com.viplearner.feature.home.presentation.component.HomeTopBar
import com.viplearner.feature.home.presentation.component.NoNoteFoundView
import com.viplearner.feature.home.presentation.model.NoteItem
import com.viplearner.feature.home.presentation.state.HomeScreenUiEvent
import com.viplearner.feature.home.presentation.state.HomeScreenUiState

@Composable
fun HomeRoute(
    onNavigateToNote: (String) -> Unit,
    onAddNoteClicked: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeScreenUiState by viewModel.homeScreenUiState.collectAsStateWithLifecycle()
    val homeScreenUiEvent by viewModel.homeScreenUiEvent.collectAsStateWithLifecycle()
    val localizationManager = rememberLocalizationManager()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val isSelectionMode =
        homeScreenUiState is HomeScreenUiState.Content.NormalMode && (homeScreenUiState as HomeScreenUiState.Content.NormalMode).isSelectionMode

    var searchValue by rememberSaveable { mutableStateOf("") }

    HomeScreen(
        searchValue = searchValue,
        localizationManager = localizationManager,
        isAllSelected = homeScreenUiState.let {
            when (it) {
                is HomeScreenUiState.Content.NormalMode -> {
                    it.list.all { it.isSelected }
                }

                else -> false
            }
        },
        onSearchTextChanged = { searchText ->
            searchValue = searchText
            viewModel.getListBySearchText(searchValue)
        },
        onSelectionModeCancelled = {
            viewModel.getList()
        },
        onSearchMode = {
            searchValue = ""
            viewModel.getListBySearchText(searchValue)
        },
        onSearchCancelled = {
            searchValue = ""
            viewModel.getList()
        },
        onSelectAll = {
            viewModel.selectAll()
        },
        onPinItems = {
            viewModel.pinNotes((homeScreenUiState as HomeScreenUiState.Content.NormalMode).list.filter { it.isSelected })
        },
        onUnpinItems = {
            viewModel.unpinNotes((homeScreenUiState as HomeScreenUiState.Content.NormalMode).list.filter { it.isSelected })
        },
        onAddNoteClick = {
            onAddNoteClicked(" ")
        },
        onDeleteNotesClicked = {
            viewModel.deleteNotes((homeScreenUiState as HomeScreenUiState.Content.NormalMode).list.filter { it.isSelected })
        },
        onItemClick = { noteItem ->
            if (!isSelectionMode) {
                onNavigateToNote.invoke(noteItem.uuid)
            } else {
                viewModel.selectNote(
                    noteItem
                )
            }
        },
        onItemLongClick = { noteItem ->
            viewModel.selectNote(
                noteItem
            )
        },
        onDeselectAll = {
            viewModel.deselectAll()
        },
        homeScreenUiState = homeScreenUiState,
        homeScreenUiEvent = homeScreenUiEvent,
        snackbarHostState = snackbarHostState
    )
}

@Composable
internal fun HomeScreen(
    searchValue: String,
    isAllSelected: Boolean,
    localizationManager: LocalizationManager,
    onAddNoteClick: () -> Unit,
    onSearchMode: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSelectionModeCancelled: () -> Unit,
    onDeleteNotesClicked: () -> Unit,
    onItemClick: (NoteItem) -> Unit,
    onItemLongClick: (NoteItem) -> Unit,
    onPinItems: () -> Unit,
    onUnpinItems: () -> Unit,
    onSelectAll: () -> Unit,
    onDeselectAll: () -> Unit,
    onSearchCancelled: () -> Unit,
    homeScreenUiState: HomeScreenUiState,
    homeScreenUiEvent: HomeScreenUiEvent,
    snackbarHostState: SnackbarHostState
) {
    Template(modifier = Modifier.background(MaterialTheme.colorScheme.background), topBar = {
        HomeTopBar(
            modifier = Modifier.fillMaxWidth(),
            searchValue = searchValue,
            isAllSelected = isAllSelected,
            onSearchMode = onSearchMode,
            onSearchTextChanged = onSearchTextChanged,
            onSelectionModeCancelled = onSelectionModeCancelled,
            onSelectAll = onSelectAll,
            onDeselectAll = onDeselectAll,
            localizationManager = localizationManager,
            onSearchCancelled = onSearchCancelled,
            homeScreenUiState = homeScreenUiState
        )
    }, bottomBar = {
        AnimatedVisibility(visible = homeScreenUiState is HomeScreenUiState.Content.NormalMode && homeScreenUiState.isSelectionMode,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()) {
            HomeBottomBar(modifier = Modifier,
                allSelectedIsPinned = homeScreenUiState is HomeScreenUiState.Content.NormalMode && homeScreenUiState.list.filter { it.isSelected }.all { it.isPinned },
                isAllDeselected = homeScreenUiState is HomeScreenUiState.Content.NormalMode && homeScreenUiState.list.all { !it.isSelected },
                onPinItems = onPinItems,
                onUnpinItems = onUnpinItems,
                onDelete = {
                    onDeleteNotesClicked()
                })
        }
    }, floatingActionButton = {
        AnimatedVisibility(
            visible = (homeScreenUiState is HomeScreenUiState.Content.NormalMode && homeScreenUiState.isSelectionMode.not()) || homeScreenUiState is HomeScreenUiState.Empty,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            NotesFloatingActionButton {
                onAddNoteClick()
            }
        }
    }) {
        HomeContent(
            modifier = Modifier
                .padding(it)
                .animateContentSize(tween())
                .padding(horizontal = 20.dp),
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick,
            homeScreenUiState = homeScreenUiState,
            homeScreenUiEvent = homeScreenUiEvent,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
internal fun HomeContent(
    modifier: Modifier = Modifier,
    onItemClick: (NoteItem) -> Unit,
    onItemLongClick: (NoteItem) -> Unit,
    homeScreenUiState: HomeScreenUiState,
    homeScreenUiEvent: HomeScreenUiEvent,
    snackbarHostState: SnackbarHostState
) {
    when (homeScreenUiState) {
        is HomeScreenUiState.Content -> {
            when (homeScreenUiState) {
                is HomeScreenUiState.Content.NormalMode -> List(
                    modifier = modifier
                        .padding()
                        .testTag(HomeTag.list),
                    isSelectionMode = homeScreenUiState.isSelectionMode,
                    listItems = homeScreenUiState.list,
                    onItemClick = onItemClick,
                    onItemLongClick = onItemLongClick
                )

                is HomeScreenUiState.Content.SearchMode -> List(modifier = modifier
                    .padding()
                    .testTag(HomeTag.list),
                    isSelectionMode = false,
                    listItems = homeScreenUiState.list,
                    onItemClick = onItemClick,
                    onItemLongClick = { })

            }
        }

        is HomeScreenUiState.Loading -> {
            ProgressDialog()
        }

        is HomeScreenUiState.Error -> {
            ErrorDialog(
                modifier = Modifier.testTag(HomeTag.errorDialog),
                errorMessage = homeScreenUiState.errorMessage
            )
        }

        is HomeScreenUiState.Empty -> {
            EmptyListView(
                modifier = modifier.testTag(HomeTag.emptyMessage),
                message = homeScreenUiState.emptyMessage
            )
        }

        is HomeScreenUiState.NoNoteFound -> {
            NoNoteFoundView(
                modifier = modifier.testTag(HomeTag.noNoteFound),
                noNoteFoundMessage = homeScreenUiState.noNoteFoundMessage
            )
        }

    }

    when (homeScreenUiEvent) {
        is HomeScreenUiEvent.Loading -> {
            ProgressDialog()
        }

        is HomeScreenUiEvent.Error -> {
            ErrorDialog(errorMessage = homeScreenUiEvent.errorMessage)
        }

        else -> Unit
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomCenter)
    ) {
        SnackbarHost(modifier = Modifier.testTag(HomeTag.snackbar), hostState = snackbarHostState)
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
fun HomeScreenPreview() {
    val searchValue by remember {
        mutableStateOf("")
    }
    var isAllSelected by remember {
        mutableStateOf(false)
    }
    val loading = HomeScreenUiState.Loading
    val content = HomeScreenUiState.Content.NormalMode(
        listOf(
            NoteItem(
                "902930",
                "How to make pancakes",
                "Whisk the eggs to make pancakes for the house to eat fro the bowl",
                18037723902,
                false,
                false
            ), NoteItem(
                "902930",
                "How to make pancakes",
                "Whisk the eggs to make pancakes for the house to eat fro the bowl",
                1897902028,
                false,
                false
            )
        ), true
    )
    val search = HomeScreenUiState.Content.SearchMode(
        listOf(
            NoteItem(
                "902930",
                "How to make pancakes",
                "Whisk the eggs to make pancakes for the house to eat fro the bowl",
                18037723902,
                false,
                false
            ), NoteItem(
                "902930",
                "How to make pancakes",
                "Whisk the eggs to make pancakes for the house to eat fro the bowl",
                1897902028,
                false,
                false
            )
        )
    )
    val empty = HomeScreenUiState.Empty("Create your first note!")
    HomeScreen(searchValue = searchValue,
        isAllSelected = isAllSelected,
        localizationManager = LocalizationManager(context = LocalContext.current),
        onSearchTextChanged = {},
        onAddNoteClick = {},
        onSearchCancelled = {},
        onSelectAll = {
            isAllSelected = true
        },
        onDeselectAll = {
            isAllSelected = false
        },
        onSearchMode = {},
        onSelectionModeCancelled = {},
        onItemClick = {},
        onUnpinItems = {},
        onPinItems = {},
        onItemLongClick = {},
        onDeleteNotesClicked = {},
        homeScreenUiState = search,
        homeScreenUiEvent = HomeScreenUiEvent.Idle,
        snackbarHostState = remember {
            SnackbarHostState()
        })
}
