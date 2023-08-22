package com.viplearner.feature.home.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.common.presentation.util.extension.epochTo12HrFormat
import com.viplearner.feature.home.presentation.model.NoteItem

@Composable
fun List(
    modifier: Modifier = Modifier,
    listItems: List<NoteItem>,
    onItemClick: (NoteItem) -> Unit,
    onItemLongClick: (NoteItem) -> Unit
) {
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(listItems) { item ->
            NoteItem(
                modifier = Modifier,
                noteItem = item,
                onItemClick = onItemClick,
                onItemLongClick = onItemLongClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    noteItem: NoteItem,
    onItemClick: (NoteItem) -> Unit,
    onItemLongClick: (NoteItem) -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onItemClick(noteItem) },
                onLongClick = { onItemLongClick(noteItem) }
            )
            .background(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)){
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = noteItem.title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = noteItem.content,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = noteItem.timeLastEdited.epochTo12HrFormat(),
                style = MaterialTheme.typography.labelSmall,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview(){
    NoteItem(
        noteItem = NoteItem(
            "902930",
            "How to make pancakes",
            "Whisk the eggs to make pancakes for the house to eat fro the bowl",
            15879023772,
            false
        ),
        onItemClick = {},
        onItemLongClick = {}
    )
}