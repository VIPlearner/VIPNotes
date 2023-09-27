package com.viplearner.feature.home.presentation.component.sign_in

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viplearner.common.presentation.component.PlaceholderText
import com.viplearner.common.presentation.component.VIPTextField

@Composable
fun SignInTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChanged: (String) -> Unit,
    thereIsNext: Boolean = true,
    onDone: () -> Unit = {},
    trailingIcon: @Composable() (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current

    VIPTextField(
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            imeAction = if(thereIsNext)ImeAction.Next else ImeAction.Done
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = {
                onDone()
            }
        ),
        placeholder = { PlaceholderText(value = placeholder) },
        innerPaddingValues = PaddingValues(vertical= 10.dp, horizontal = 20.dp),
        trailingIcon = trailingIcon,
        shape = CircleShape,
        singleLine = true,
        value = value,
        visualTransformation = visualTransformation,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,

        )
    )
}

@Preview(showBackground = true)
@Composable
fun SignInTextFieldPreview() {
    var value by remember {
        mutableStateOf("")
    }
    SignInTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = value,
        onValueChanged = {
            value = it
        },
        thereIsNext = true,
        placeholder = "Email address",
    )
}