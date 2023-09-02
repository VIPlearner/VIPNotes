package com.viplearner.feature.home.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eemmez.localization.LocalizationManager
import com.viplearner.feature.home.presentation.R
import com.viplearner.feature.home.presentation.model.NoteItem
import com.viplearner.feature.home.presentation.state.HomeScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier,
    isAllSelected: Boolean,
    searchValue: String,
    localizationManager: LocalizationManager,
    onSearchMode: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSelectionModeCancelled: () -> Unit,
    onSelectAll: () -> Unit,
    onDeselectAll: () -> Unit,
    onSearchCancelled: () -> Unit,
    homeScreenUiState: HomeScreenUiState
) {
    val searchMode = homeScreenUiState is HomeScreenUiState.Content.SearchMode || homeScreenUiState is HomeScreenUiState.NoNoteFound
    val isSelectionMode =
        homeScreenUiState is HomeScreenUiState.Content.NormalMode && homeScreenUiState.isSelectionMode
    val searchClickEnabled = homeScreenUiState is HomeScreenUiState.Content.NormalMode
    Column(modifier = modifier) {
        TopAppBar(
            modifier = Modifier
                .animateContentSize(tween())
                .then(
                    if (!isSelectionMode) Modifier.height(0.dp)
                    else Modifier
                ),
            title = {
                Text(text = localizationManager.getString(R.string.select_items))
            },
            navigationIcon = {
                IconButton(onClick = onSelectionModeCancelled) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = localizationManager.getString(R.string.cancel_selection_mode)
                    )
                }
            },
            actions = {
                TextButton(
                    onClick = {
                        if (!isAllSelected) onSelectAll() else onDeselectAll()
                    }
                ) {
                    Text(
                        text = if (!isAllSelected)
                            localizationManager.getString(
                                R.string.select_all
                            ) else
                                localizationManager.getString(
                                    R.string.deselect_all
                                )
                    )
                }
            }
        )
        SearchBox(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            searchMode = searchMode,
            onSearchMode = onSearchMode,
            searchClickEnabled = searchClickEnabled,
            searchValue = searchValue,
            onTextChanged = onSearchTextChanged,
            onSearchCancelled = onSearchCancelled
        )
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    val list = listOf(
        NoteItem(
            "902930",
            "How to make pancakes",
            "Whisk the eggs to make pancakes for the house to eat fro the bowl",
            18037723902,
            false,
            false
        ),
        NoteItem(
            "902930",
            "How to make pancakes",
            "Whisk the eggs to make pancakes for the house to eat fro the bowl",
            1897902028,
            false,
            false
        )
    )
    var homeScreenUiState: HomeScreenUiState by remember {
        mutableStateOf(HomeScreenUiState.Content.NormalMode(list, true))
    }
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    var isAllSelected by remember {
        mutableStateOf(false)
    }
    val localizationManager = LocalizationManager(context = LocalContext.current)
    HomeTopBar(
        modifier = Modifier,
        searchValue = searchValue,
        isAllSelected = isAllSelected,
        onSearchTextChanged = { searchValue = it },
        onSelectionModeCancelled = {
            homeScreenUiState = HomeScreenUiState.Content.NormalMode(list, false)
        },
        onSearchMode = {
            homeScreenUiState = HomeScreenUiState.Content.SearchMode(list)
        },
        onSelectAll = { isAllSelected = true },
        onDeselectAll = { isAllSelected = false },
        onSearchCancelled = {
            homeScreenUiState = HomeScreenUiState.Content.NormalMode(list, false)
        },
        localizationManager = localizationManager,
        homeScreenUiState = homeScreenUiState
    )
}