package com.viplearner.feature.single_note.presentation.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun singleNoteViewModelProvider(
    factory: SingleNoteViewModel.Factory,
    noteUUID: String,
): SingleNoteViewModel {
    return viewModel(factory = SingleNoteViewModel.provideFactory(
        factory,
        noteUUID,
    ))
}

