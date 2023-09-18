package com.viplearner.feature.home.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichText
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.viplearner.common.presentation.util.extension.epochTo12HrFormat
import com.viplearner.feature.home.presentation.model.NoteItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    isSelectionMode: Boolean,
    noteItem: NoteItem,
    onItemClick: (NoteItem) -> Unit,
    onItemLongClick: (NoteItem) -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = { onItemClick(noteItem) },
                onLongClick = { onItemLongClick(noteItem) }
            )
            .background(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier
                .weight(1f)
            ) {
                Text(
                    modifier = Modifier,
                    text = noteItem.title,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                val richTextState = rememberRichTextState()
                richTextState.setMarkdown(noteItem.content)
                Text(
                    modifier = Modifier,
                    text = richTextState.annotatedString.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier,
                        text = noteItem.timeLastEdited.epochTo12HrFormat(),
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1
                    )
                    AnimatedVisibility(visible = noteItem.isPinned) {
                        Icon(
                            imageVector = Icons.Default.PushPin,
                            contentDescription = "pin",
                            modifier = Modifier
                                .size(12.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            AnimatedVisibility(
                visible = isSelectionMode,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .animateContentSize(tween())
                        .size(25.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                ) {
                    if (noteItem.isSelected) {
                        Surface(
                            modifier = Modifier
                                .size(if (noteItem.isSelected) 25.dp else 0.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(4.dp),
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected Note ${noteItem.title}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview(){
    var isSelectionMode by remember {
        mutableStateOf(true)
    }
    var noteItem by remember {
        mutableStateOf(
            NoteItem(
                "902930",
                "How to make pancakes Whisk the eggs to make pancakes for the house to eat fro the bowl Whisk the eggs to make pancakes for the house to eat fro the bowl Whisk the eggs to make pancakes for the house to eat fro the bowl",
                "Chair Nail cutter Something for smellas jdidjdd **hsidj** ***hsjsj*** ***usieiej*** • ***gbo adura mi ooo*** • ***2020 tun tu sun mo*** heiebsh why are we here • seje ",
                15879023772,
                false,
                false
            )
        )
    }
    NoteItem(
        isSelectionMode = isSelectionMode,
        noteItem = noteItem,
        onItemClick = {
            if(isSelectionMode){
                noteItem = noteItem.copy(isSelected = !noteItem.isSelected)
            }
        },
        onItemLongClick = {
            isSelectionMode = !isSelectionMode
            noteItem = noteItem.copy(isSelected = !noteItem.isSelected)
        }
    )
}