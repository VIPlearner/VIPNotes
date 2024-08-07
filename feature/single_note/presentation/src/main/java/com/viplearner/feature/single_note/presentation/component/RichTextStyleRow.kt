package com.mohamedrejeb.richeditor.sample.common

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.FormatAlignRight
import androidx.compose.material.icons.outlined.FormatBold
import androidx.compose.material.icons.outlined.FormatItalic
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.FormatUnderlined
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.viplearner.feature.single_note.presentation.component.LinkModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTextStyleRow(
    modifier: Modifier = Modifier,
    state: RichTextState
) {
    var showLinkModal by remember {
        mutableStateOf(false)
    }
    if(showLinkModal){
        ModalBottomSheet(onDismissRequest = { showLinkModal = false }) {
            LinkModal(
                onDone = { text, url->
                    state.addLink(text, url)
                    showLinkModal = false
                }
            )
        }
    }
    Surface(
        color = MaterialTheme.colorScheme.outline,
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
    ){
        Row(
            modifier = modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 5.dp, vertical = 8.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RichTextStyleButton(
                enabled = state.currentSpanStyle.fontWeight == FontWeight.Bold,
                onValueChanged = if (state.currentSpanStyle.fontWeight == FontWeight.Bold) {
                    { state.removeSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) }
                } else {
                    { state.addSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) }
                },
                icon = Icons.Outlined.FormatBold
            )

            RichTextStyleButton(
                enabled = state.currentSpanStyle.fontStyle == FontStyle.Italic,
                onValueChanged =
                {
                    if (state.currentSpanStyle.fontStyle == FontStyle.Italic) {
                        state.removeSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    } else {
                        state.addSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    }
                },
                icon = Icons.Outlined.FormatItalic
            )

            RichTextStyleButton(
                enabled = state.currentSpanStyle.textDecoration == TextDecoration.Underline,
                onValueChanged =
                {
                    if (state.currentSpanStyle.textDecoration == TextDecoration.Underline) {
                        state.removeSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                    } else {
                        state.addSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                    }
                },
                icon = Icons.Outlined.FormatUnderlined
            )

            RichTextStyleButton(
                enabled = state.isUnorderedList,
                onValueChanged =
                {
                    if (!state.isUnorderedList) {
                        state.addUnorderedList()
                    } else {
                        state.removeUnorderedList()
                    }
                },
                icon = Icons.Outlined.FormatListBulleted
            )

            RichTextStyleButton(
                enabled = state.currentParagraphStyle.textAlign == TextAlign.Center,
                onValueChanged =
                {
                    if (state.currentParagraphStyle.textAlign == TextAlign.Center) {
                        state.removeParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                    } else {
                        state.addParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                    }
                },
                icon = Icons.Outlined.FormatAlignCenter
            )

            RichTextStyleButton(
                enabled = state.currentParagraphStyle.textAlign == TextAlign.Right,
                onValueChanged =
                {
                    if (state.currentParagraphStyle.textAlign == TextAlign.Right) {
                        state.removeParagraphStyle(ParagraphStyle(textAlign = TextAlign.Right))
                    } else {
                        state.addParagraphStyle(ParagraphStyle(textAlign = TextAlign.Right))
                    }
                },
                icon = Icons.Outlined.FormatAlignRight
            )

//            RichTextStyleButton(
//                enabled = state.isLink,
//                onValueChanged =
//                {
//                    if(!state.isLink){ showLinkModal = true }
//                },
//                icon = Icons.Outlined.Link
//            )

//            RichTextStyleButton(
//                enabled = state.isOrderedList,
//                onValueChanged =
//                {
//                    if (!state.isOrderedList) {
//                        state.addOrderedList()
//                    } else {
//                        state.removeOrderedList()
//                    }
//                },
//                icon = Icons.Outlined.FormatListNumbered
//            )
//
//        RichTextStyleButton(
//            style = RichTextStyle.TextColor(Color.Red),
//            value = value,
//            onValueChanged = onValueChanged,
//            icon = Icons.Filled.Circle,
//            tint = Color.Red
//        )
//
//        RichTextStyleButton(
//            style = CustomStyle(color = Color.White, background = Color.Green),
//            value = value,
//            onValueChanged = onValueChanged,
//            icon = Icons.Outlined.Circle,
//            tint = Color.Green
//        )
        }
    }
}

@Preview
@Composable
fun RichTextStyleRowPreview(){
    RichTextStyleRow(
        state = rememberRichTextState()
    )
}