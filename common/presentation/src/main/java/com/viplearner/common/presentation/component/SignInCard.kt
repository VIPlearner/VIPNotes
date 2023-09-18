package com.viplearner.common.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.NotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    color: Color = Color.Transparent,
    onClick: () -> Unit,
){
    Surface(
        modifier = modifier,
        color = color,
        shape = CircleShape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(30.dp).align(Alignment.CenterVertically)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun SignInCardPreview(){
    NotesTheme{
        SignInCard(
            modifier = Modifier
                .width(150.dp)
                .padding(),
            text = "Email",
            icon = Icons.Default.Email,
            onClick = {}
        )
    }
}