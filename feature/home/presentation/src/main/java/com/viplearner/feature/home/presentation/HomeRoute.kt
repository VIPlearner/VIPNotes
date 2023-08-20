package com.viplearner.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.viplearner.common.presentation.component.EmptyMessage
import com.viplearner.common.presentation.component.ErrorDialog
import com.viplearner.common.presentation.component.ProgressDialog
import com.viplearner.feature.home.presentation.component.EmptyListView
import com.viplearner.feature.home.presentation.component.List
import com.viplearner.feature.home.presentation.component.SearchBox
import com.viplearner.feature.home.presentation.component.Template
import com.viplearner.feature.home.presentation.model.NoteItem
import com.viplearner.feature.home.presentation.state.HomeScreenUiEvent
import com.viplearner.feature.home.presentation.state.HomeScreenUiState

@Composable
fun HomeRoute(
    onItemClick: (NoteItem) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeScreenUiState by viewModel.homeScreenUiState.collectAsStateWithLifecycle()
    val homeScreenUiEvent by viewModel.homeScreenUiEvent.collectAsStateWithLifecycle()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    HomeScreen(
        onTextChanged = { searchText ->
            viewModel.getList()
        },
        onItemClick = { homeListItem ->
            onItemClick.invoke(homeListItem)
        },
        onItemLongClick = { homeListItem ->
//            viewModel.addNote(homeListItem)
        },
        homeScreenUiState = homeScreenUiState,
        homeScreenUiEvent = homeScreenUiEvent,
        snackbarHostState = snackbarHostState
    )
}

@Composable
internal fun HomeScreen(
    onTextChanged: (String) -> Unit,
    onItemClick: (NoteItem) -> Unit,
    onItemLongClick: (NoteItem) -> Unit,
    homeScreenUiState: HomeScreenUiState,
    homeScreenUiEvent: HomeScreenUiEvent,
    snackbarHostState: SnackbarHostState
) {
    Template(
        modifier = Modifier,
        topBar = {
            SearchBox(
                modifier = Modifier,
                onTextChanged = onTextChanged
            )
        },
        floatingActionButton = { /**TODO**/ }
    ) {
        HomeContent(
            modifier = Modifier.padding(it),
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
            List(
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .testTag(HomeTag.list),
                listItems = homeScreenUiState.list,
                onItemClick = onItemClick,
                onItemLongClick = onItemLongClick
            )
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
                modifier = Modifier.testTag(HomeTag.emptyMessage),
                message = homeScreenUiState.emptyMessage
            )
        }
    }

    when (homeScreenUiEvent) {
        is HomeScreenUiEvent.AddNoteSuccess -> {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(homeScreenUiEvent.successMessage)
            }
        }

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

@Preview(backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
fun HomeScreenPreview(){
    val loading = HomeScreenUiState.Loading
    val content = HomeScreenUiState.Content(
        listOf(
            NoteItem(
                "902930",
                "How to make pancakes",
                "Whisk the eggs to make pancakes for the house to eat fro the bowl",
                "6:43 PM"
            ),
            NoteItem(
                "902930",
                "How to make pancakes",
                "Whisk the eggs to make pancakes for the house to eat fro the bowl",
                "6:43 PM"
            )
        )
    )
    val empty = HomeScreenUiState.Empty("Create your first note!")
    HomeScreen(
        onTextChanged = {},
        onItemClick = {},
        onItemLongClick = {},
        homeScreenUiState = empty,
        homeScreenUiEvent = HomeScreenUiEvent.Idle,
        snackbarHostState = remember{
            SnackbarHostState()
        }
    )
}
