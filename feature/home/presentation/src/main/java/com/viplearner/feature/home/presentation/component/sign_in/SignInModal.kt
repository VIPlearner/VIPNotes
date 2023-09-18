package com.viplearner.feature.home.presentation.component.sign_in

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viplearner.common.domain.entity.UserData
import com.viplearner.common.presentation.component.ProgressDialog
import com.viplearner.common.presentation.component.SignInButtons
import com.viplearner.common.presentation.component.SignInTextField
import com.viplearner.common.presentation.component.SignUpButtons
import com.viplearner.common.presentation.component.VIPTextField
import com.viplearner.common.presentation.util.rememberLocalizationManager
import com.viplearner.feature.home.presentation.R
import com.viplearner.feature.home.presentation.state.SignInState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInModal(
    isSyncingData: Boolean,
    signInState: SignInState,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSignInWithEmail: (email: String, password: String) -> Unit,
    onClickSignUp: () -> Unit,
    onSignInWithGoogle: () -> Unit,
    onSignInWithFacebook: () -> Unit,
    onSyncData: () -> Unit,
    onSignOut: () -> Unit,
    onSignUpWithEmail: (email: String, password: String) -> Unit,
    ) {
    val localizationManager = rememberLocalizationManager()
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {

        when(signInState){
            SignInState.Init -> {
                var email by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var isPasswordVisible by remember {
                    mutableStateOf(false)
                }
                Column {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.empty_note),
                        contentDescription = null
                    )
                    VIPTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        }
                    )
                    VIPTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = if(!isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = null,
                                tint = LocalContentColor.current
                            )
                        }
                    )

                    SignInButtons(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        onSignInWithGoogle = onSignInWithGoogle,
                        onSignInWithFacebook = onSignInWithFacebook,
                        onSignInWithEmail = {
                            onSignInWithEmail(email, password)
                                            },
                        onClickSignUp = onClickSignUp
                    )
                }
            }
            SignInState.Loading -> {
                ProgressDialog(modifier = Modifier.fillMaxWidth())
            }
            is SignInState.SignInSuccess -> {
                Column {
                    //TODO :: Replace with User info
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.empty_note),
                        contentDescription = null
                    )
                    SignInItem(
                        icon = Icons.Default.Sync,
                        text = localizationManager.getString(R.string.sync_notes),
                        isLoading = isSyncingData
                    ){
                        onSyncData()
                    }
                    CompositionLocalProvider(
                        LocalContentColor provides Color.Red
                    ) {
                        SignInItem(
                            icon = Icons.Default.Logout,
                            text = localizationManager.getString(R.string.sign_out),
                            isLoading = false
                        ){
                            onSignOut()
                        }
                    }

                }
            }
            SignInState.SignUp -> {
                var email by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var isPasswordVisible by remember {
                    mutableStateOf(false)
                }
                Column {
                    SignInTextField(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        value = email,
                        onValueChanged = {
                            email = it
                        },
                        placeholder = "Enter your email"
                    )
                    SignInTextField(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        value = password,
                        onValueChanged = {
                            password = it
                        },
                        placeholder = "Enter your password",
                        trailingIcon = {
                            Icon(
                                imageVector = if(!isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = null,
                                tint = LocalContentColor.current
                            )
                        }
                    )

                    SignUpButtons(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        onSignUpWithGoogle = onSignInWithGoogle,
                        onSignUpWithFacebook = onSignInWithFacebook,
                        onSignUpWithEmail = { onSignUpWithEmail(email,password) },
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onClick: () -> Unit
){
    Card(
        modifier = modifier,
        onClick = onClick,
        enabled = !isLoading,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = LocalContentColor.current
        )
    ){
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = LocalContentColor.current
            )
            Text(
                text = text,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = LocalContentColor.current
            )
            AnimatedVisibility(visible = isLoading) {
                CircularProgressIndicator(
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignInModalPreview() {
    var signInState: SignInState by remember {
        mutableStateOf(SignInState.SignInSuccess(
            userData = UserData(
                "1",
                "Mohamed Rejeb",
                "www.google.com"
            )
        )
        )
    }
    SignInModal(
        isSyncingData = false,
        signInState = signInState,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        onDismissRequest = {},
        onSignInWithEmail = {email, password ->
            signInState = SignInState.SignInSuccess(
                userData = UserData(
                    "1",
                    "johndoe@gmail.com",
                    "www.google.com"
                )
            )
        },
        onClickSignUp = {},
        onSignInWithGoogle = {
            signInState = SignInState.Loading
        },
        onSignInWithFacebook = {
            signInState = SignInState.Loading
        },
        onSignUpWithEmail = { email, password ->
            signInState = SignInState.SignInSuccess(
                userData = UserData(
                    "1",
                    "Mohamed Rejeb",
                    "www.google.com"
                )
            )
        },
        onSyncData = {

        },
        onSignOut = {
            signInState = SignInState.Init
        }
    )
}