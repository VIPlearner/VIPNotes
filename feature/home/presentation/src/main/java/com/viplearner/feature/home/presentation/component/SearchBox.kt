package com.viplearner.feature.home.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.viplearner.common.presentation.component.VIPTextField
import com.viplearner.common.presentation.util.rememberLocalizationManager
import com.viplearner.feature.home.presentation.HomeTag
import com.viplearner.feature.home.presentation.R

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    searchValue: String,
    searchMode: Boolean,
    profileImageUrl: String?,
    searchClickEnabled: Boolean,
    onSearchMode: () -> Unit,
    onTextChanged: (String) -> Unit,
    onSearchCancelled: () -> Unit,
    onClickProfile: () -> Unit,
) {
    val searchFocus = remember {
        FocusRequester()
    }
    val localizationManager = rememberLocalizationManager()
    Row(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        VIPTextField(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .align(Alignment.CenterVertically)
                .focusRequester(searchFocus)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = {
                        if (searchClickEnabled) {
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
                    text = localizationManager.getString(R.string.search),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchMode,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(22.dp)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            onTextChanged("")
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.CenterVertically),
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Cancel Search"
                        )
                    }
                }
                AnimatedVisibility(
                    visible = !searchMode,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    if (profileImageUrl != null)

                        IconButton(
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            onClick = {
                                onClickProfile()
                            },
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onBackground.copy(0.3f)
                            )
                        ) {
                            AsyncImage(
                                model = profileImageUrl,
                                modifier = Modifier
                                    .padding(1.dp)
                                    .clip(CircleShape),
                                contentDescription = localizationManager.getString(R.string.profile_picture)
                            )
                        }
                    else
                        IconButton(
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            onClick = {
                                onClickProfile()
                            },
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.outline.copy(0.3f),
                                contentColor = MaterialTheme.colorScheme.onBackground.copy(0.3f)
                            )
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .align(Alignment.CenterVertically),
                                imageVector = Icons.Default.Person,
                                contentDescription = localizationManager.getString(R.string.profile_picture)
                            )
                        }
                }
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
        mutableStateOf(false)
    }
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    var searchClickEnabled by rememberSaveable {
        mutableStateOf(false)
    }
    val profileImageUrl = "https://www.gravatar.com/avatar/2433495de6d2b99746f8e25344209fa7?s=64&d=identicon&r=PG"
    SearchBox(
        searchValue = searchValue,
        searchMode = searchMode,
        searchClickEnabled = searchClickEnabled,
        onTextChanged = { searchValue = it },
        onSearchMode = { searchMode = true },
        profileImageUrl = null,
        onSearchCancelled = {
            searchValue = ""
            searchMode = false
            searchClickEnabled = true
        },
        onClickProfile = {}
    )
}