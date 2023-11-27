package com.viplearner.feature.single_note.presentation.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viplearner.common.presentation.component.PlaceholderText
import com.viplearner.common.presentation.component.VIPTextField

@Composable
fun LinkModal(
    modifier: Modifier = Modifier,
    onDone: (text: String, link: String) -> Unit
){
    val context = LocalContext.current
    var text by rememberSaveable {
        mutableStateOf("")
    }
    var link by rememberSaveable {
        mutableStateOf("")
    }

    val onInternalDone = {
        if(link.isBlank()){
            Toast.makeText(context, "Link cannot be empty", Toast.LENGTH_SHORT).show()
        } else{
            onDone(text.ifBlank { link }, link)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            val focusManager = LocalFocusManager.current

            VIPTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Next
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
                placeholder = { PlaceholderText(value = "Text") },
                innerPaddingValues = PaddingValues(vertical= 10.dp, horizontal = 20.dp),
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,

                    )
            )
            Spacer(modifier = Modifier.height(10.dp))
            VIPTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onInternalDone()
                    },
                ),
                placeholder = { PlaceholderText(value = "Link") },
                innerPaddingValues = PaddingValues(vertical= 10.dp, horizontal = 20.dp),
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                value = link,
                onValueChange = { link = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                onClick = onInternalDone
            ) {
                Text(
                    text = "Add Link"
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LinkModalPreview(){
    LinkModal(
        modifier = Modifier,
        onDone = { _, _ -> }
    )
}