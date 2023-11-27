package com.viplearner.notes.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.viplearner.common.presentation.component.ErrorDialog
import com.viplearner.common.presentation.component.Template
import com.viplearner.notes.settings.presentation.component.SettingsItem
import com.viplearner.notes.settings.presentation.state.SettingsUiEvent

@Composable
fun SettingsRoute(
    onBackClicked: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val syncState by viewModel.syncState.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    val settingsUiEvent by viewModel.settingsUiEvent.collectAsState()

    SettingsScreen(
        syncState = syncState,
        settingsUiEvent = settingsUiEvent,
        onBackClicked = onBackClicked,
        onSetSyncState = viewModel::setSyncState,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    syncState: Boolean,
    settingsUiEvent: SettingsUiEvent,
    onBackClicked: () -> Unit,
    onSetSyncState: (Boolean) -> Unit,
){
    Template(
       topBar = {
           TopAppBar(
               navigationIcon = {
                   IconButton(onClick = onBackClicked){
                       Icon(
                           imageVector = Icons.Default.ArrowBackIosNew,
                           contentDescription = null,
                       )
                   }
               },
               title = {
                   Text(
                       text = "Settings",
                       style = MaterialTheme.typography.titleMedium
                   )
               }
           )
       }
    ) {paddingValues ->
        SettingsContent(
            modifier = Modifier.padding(paddingValues),
            syncState = syncState,
            settingsUiEvent = settingsUiEvent,
            onSetSyncState = onSetSyncState,
        )
    }

}

@Composable
internal fun SettingsContent(
    modifier: Modifier = Modifier,
    syncState: Boolean,
    settingsUiEvent: SettingsUiEvent,
    onSetSyncState: (Boolean) -> Unit,
){
    Surface(modifier = modifier) {
        Column {
            SettingsItem(
                text = "Sync Notes Online Automatically",
                value = syncState,
                onValueChange = onSetSyncState,
            )
        }
    }
    when(settingsUiEvent){
        is SettingsUiEvent.Error -> {
            ErrorDialog(errorMessage = settingsUiEvent.message)
        }
        else -> {}
    }

}