package com.viplearner.feature.single_note.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.NotesTheme
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.sample.common.RichTextStyleRow
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesRichTextEditor(
    modifier: Modifier = Modifier,
    initialValue: String ,
    onValueChanged: (String) -> Unit
) {
    val richTextEditorState = rememberRichTextState()
    var inFocus by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) { richTextEditorState.setMarkdown(initialValue) }
    LaunchedEffect(richTextEditorState.annotatedString){
        onValueChanged(richTextEditorState.toMarkdown())
    }
//    LaunchedEffect(richTextEditorState.annotatedString) {
//        onValueChanged(richTextEditorState.toMarkdown())
//    }
    Column(modifier = modifier){
        OutlinedRichTextEditor(
            state = richTextEditorState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .onFocusChanged { focusState ->
                    inFocus = focusState.hasFocus
                }
                .weight(1f),
            colors = RichTextEditorDefaults.outlinedRichTextEditorColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.onBackground,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    modifier = Modifier,
                    text = "Start typing...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            },
            contentPadding = PaddingValues(4.dp)
        )
        if(inFocus){
            RichTextStyleRow(
                modifier = Modifier.fillMaxWidth(),
                state = richTextEditorState
            )
        }
    }
}

@Preview
@Composable
fun NotesRichTextEditorPreview() {
    Timber.plant(Timber.DebugTree())
    NotesTheme{
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            NotesRichTextEditor(
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
                    .imePadding(),
                initialValue = "Ask about me",
                onValueChanged = {
                    Timber.d("New value is ${it}")
                }
            )
        }
    }
}
