package com.viplearner.feature.home.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viplearner.common.presentation.component.VIPTextField
import com.viplearner.feature.home.presentation.HomeTag

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    searchValue: String,
    searchMode: Boolean,
    searchClickEnabled: Boolean,
    onSearchMode: () -> Unit,
    onTextChanged: (String) -> Unit,
    onSearchCancelled: () -> Unit
) {
    val searchFocus = remember {
        FocusRequester()
    }
    Row(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        VIPTextField(
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .align(Alignment.CenterVertically)
                .focusRequester(searchFocus)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = {
                        if(searchClickEnabled){
                            searchFocus.captureFocus()
                            onSearchMode()
                        }
                    }
                )
                .testTag(HomeTag.searchBox),
            value = searchValue,
            innerPaddingValues = PaddingValues(vertical = 2.dp, horizontal = 15.dp),
            onValueChange = {
                onTextChanged.invoke(it)
            },
            placeholder = {
                Text(
                    text = "Search...",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            enabled = searchMode,
            singleLine = true,
            shape = CircleShape,
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
                autoCorrect = true
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                disabledContainerColor = MaterialTheme.colorScheme.outlineVariant,
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        AnimatedVisibility(
            visible = searchMode,
        ){
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                onClick = {
                    onSearchCancelled()
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Cancel Search"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    var searchMode by rememberSaveable {
        mutableStateOf(true)
    }
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    var searchClickEnabled by rememberSaveable {
        mutableStateOf(false)
    }
    SearchBox(
        searchValue = searchValue,
        searchMode = searchMode,
        searchClickEnabled = searchClickEnabled,
        onTextChanged = { searchValue = it },
        onSearchMode = { searchMode = true },
        onSearchCancelled = {
            searchValue = ""
            searchMode = false
            searchClickEnabled = true
        }
    )
}