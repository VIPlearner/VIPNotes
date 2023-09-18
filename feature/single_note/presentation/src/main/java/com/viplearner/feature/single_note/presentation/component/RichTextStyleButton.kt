package com.mohamedrejeb.richeditor.sample.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatBold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.NotesTheme
import com.mohamedrejeb.richeditor.model.RichTextStyle
import com.mohamedrejeb.richeditor.model.RichTextValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTextStyleButton(
    enabled: Boolean,
    onValueChanged: () -> Unit,
    icon: ImageVector,
) {
    Icon(
        icon,
        contentDescription = icon.name,
        tint = if(enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .clickable { onValueChanged() }
            .padding(4.dp)
    )
}

@Preview
@Composable
fun RichTextStyleButtonPreview(){
    NotesTheme{
        var enabled by remember { mutableStateOf(true) }
        RichTextStyleButton(
            enabled = enabled,
            onValueChanged = { enabled = !enabled },
            icon = Icons.Outlined.FormatBold
        )
    }
}