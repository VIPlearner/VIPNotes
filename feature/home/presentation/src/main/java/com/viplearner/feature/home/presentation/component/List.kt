package com.viplearner.feature.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.viplearner.feature.home.presentation.model.NoteItem

@Composable
fun List(
    modifier: Modifier = Modifier,
    isSelectionMode: Boolean,
    listItems: List<NoteItem>,
    onItemClick: (NoteItem) -> Unit,
    onItemLongClick: (NoteItem) -> Unit
) {
    LazyColumn(
        modifier.clip(RoundedCornerShape(10.dp)),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(listItems) { _, item ->
            NoteItem(
                modifier = Modifier,
                isSelectionMode = isSelectionMode,
                noteItem = item,
                onItemClick = onItemClick,
                onItemLongClick = onItemLongClick
            )
        }
    }
}

