package com.viplearner.common.presentation.component

import android.webkit.WebView.VisualStateCallback
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SignInTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    trailingIcon: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    VIPTextField(
        modifier = modifier.fillMaxWidth().height(50.dp),
        value = value,
        onValueChange = onValueChanged,
        trailingIcon = trailingIcon,
        placeholder = { PlaceholderText(placeholder)},
        visualTransformation = visualTransformation,
        shape = CircleShape,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        innerPaddingValues = PaddingValues(horizontal = 20.dp)

    )
}

@Composable
fun PlaceholderText(placeholder: String) {
    Text(
        text = placeholder
    )
}

@Preview
@Composable
fun SignInTextFieldPreview() {
    var value by remember {
        mutableStateOf("")
    }
    SignInTextField(
        modifier = Modifier.padding(15.dp),
        value = value,
        onValueChanged = { value = it },
        placeholder = "Email",
        trailingIcon = {}
    )
}