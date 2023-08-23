package com.viplearner.feature.single_note.presentation.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.viplearner.common.presentation.component.VIPTextField

@Composable
fun SingleNoteTextField(
    modifier: Modifier = Modifier,
    placeholderText: String,
    textStyle: TextStyle,
    value: String,
    onValueChange: (String) -> Unit
){
    VIPTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onBackground,
        ),
        placeholder = {
            Text(
                modifier = Modifier,
                text = placeholderText,
                style = textStyle,
                color = MaterialTheme.colorScheme.outlineVariant
            )
         },
        shape = RectangleShape,
    )
}

@Preview
@Composable
fun SingleNoteTextFieldPreview(){
    var value by remember {
        mutableStateOf("")
    }
    SingleNoteTextField(
        Modifier.wrapContentHeight(),
        "Title",
        MaterialTheme.typography.bodyLarge,
        value
    ) {
        value = it
    }
}