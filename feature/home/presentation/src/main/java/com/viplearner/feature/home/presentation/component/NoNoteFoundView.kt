package com.viplearner.feature.home.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.viplearner.feature.home.presentation.R

@Composable
fun NoNoteFoundView(
    modifier: Modifier = Modifier,
    noNoteFoundMessage: String
){
    EmptyListView(
        modifier = modifier,
        imgRes = R.drawable.no_note_found,
        message = noNoteFoundMessage
    )
}