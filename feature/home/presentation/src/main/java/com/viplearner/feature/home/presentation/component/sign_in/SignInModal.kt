package com.viplearner.feature.home.presentation.component.sign_in

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viplearner.common.domain.entity.UserData
import com.viplearner.common.presentation.component.ProgressDialog
import com.viplearner.common.presentation.component.SignInButtons
import com.viplearner.common.presentation.component.SignUpButtons
import com.viplearner.common.presentation.util.extension.autofill
import com.viplearner.common.presentation.util.rememberLocalizationManager
import com.viplearner.feature.home.presentation.R
import com.viplearner.feature.home.presentation.state.SignInState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInModal(
    isSyncingData: Boolean,
    signInState: SignInState,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSignInWithEmail: (email: String, password: String) -> Unit,
    onClickSignIn: () -> Unit,
    onClickSignUp: () -> Unit,
    onSignInWithGoogle: () -> Unit,
    onSignInWithFacebook: () -> Unit,
    onSyncData: () -> Unit,
    onSignOut: () -> Unit,
    onSignUpWithEmail: (email: String, password: String) -> Unit,
    ) {
    val localizationManager = rememberLocalizationManager()
    val context = LocalContext.current
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
                val onDone: () -> Unit = {
                    if(password.length < 8) Toast.makeText(
                        context,
                        localizationManager.getString(R.string.passwords_too_short),
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        onDismissRequest()
                        onSignInWithEmail(email, password)
                    }
                }
                val onEmailValueChange = { it: String ->
                    email = it
                }
                val onPasswordValueChange = { it: String ->
                    password = it
                }
                Column {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.empty_note),
                        contentDescription = null
                    )
                    SignInTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .autofill(
                                autofillTypes = listOf(
                                    AutofillType.EmailAddress,
                                    AutofillType.Username
                                ),
                                onAutoFilled = onEmailValueChange
                            )
                            .padding(horizontal = 20.dp),
                        value = email,
                        placeholder = "Email address",
                        onValueChanged = {
                            email = it
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    SignInTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .autofill(
                                autofillTypes = listOf(AutofillType.Password),
                                onAutoFilled = onPasswordValueChange
                            )
                            .padding(horizontal = 20.dp),
                        value = password,
                        thereIsNext = false,
                        visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChanged = onPasswordValueChange,
                        placeholder = "Password",
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.clickable {
                                    isPasswordVisible = !isPasswordVisible
                                },
                                imageVector = if(!isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = null,
                                tint = LocalContentColor.current
                            )
                        },
                        onDone = onDone
                    )

                    SignInButtons(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        onSignInWithGoogle = onSignInWithGoogle,
                        onSignInWithFacebook = onSignInWithFacebook,
                        onSignInWithEmail = { onDone() },
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
                var confirmPassword by remember {
                    mutableStateOf("")
                }
                var isPasswordVisible by remember {
                    mutableStateOf(false)
                }
                var isConfirmPasswordVisible by remember {
                    mutableStateOf(false)
                }
                val onDone: () -> Unit = {
                    if(password != confirmPassword) Toast.makeText(
                        context,
                        localizationManager.getString(R.string.passwords_dont_match),
                        Toast.LENGTH_SHORT
                    ).show()
                    else if(!password.isValidPassword())Toast.makeText(
                        context,
                        localizationManager.getString(R.string.passwords_not_strong),
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        onDismissRequest()
                        onSignUpWithEmail(email, password)
                    }
                }
                val onEmailValueChange = { it: String ->
                    email = it
                }
                val onPasswordValueChange = { it: String ->
                    password = it
                }
                val onConfirmPasswordValueChange = {it: String ->
                    confirmPassword = it
                }
                Column {
                    SignInTextField(
                        modifier = Modifier
                            .autofill(
                                autofillTypes = listOf(
                                    AutofillType.EmailAddress,
                                    AutofillType.NewUsername
                                ),
                                onAutoFilled = onEmailValueChange
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        value = email,
                        onValueChanged = onEmailValueChange,
                        placeholder = "Email address"
                    )
                    SignInTextField(
                        modifier = Modifier
                            .autofill(
                                autofillTypes = listOf(AutofillType.NewPassword),
                                onAutoFilled = {
                                    onPasswordValueChange(it)
                                    onConfirmPasswordValueChange(it)
                                }
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        value = password,
                        onValueChanged = onPasswordValueChange,
                        visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        placeholder = "Password",
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.clickable {
                                    isPasswordVisible = !isPasswordVisible
                                },
                                imageVector = if(!isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = null,
                                tint = LocalContentColor.current
                            )
                        }
                    )

                    SignInTextField(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        value = confirmPassword,
                        onValueChanged = onConfirmPasswordValueChange,
                        placeholder = "Confirm Password",
                        thereIsNext = false,
                        visualTransformation = if(isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.clickable {
                                    isConfirmPasswordVisible = !isConfirmPasswordVisible
                                },
                                imageVector = if(!isConfirmPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = null,
                                tint = LocalContentColor.current
                            )
                        },
                        onDone = {
                            onDone()
                        }
                    )

                    SignUpButtons(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        onSignUpWithGoogle = onSignInWithGoogle,
                        onSignUpWithFacebook = onSignInWithFacebook,
                        onSignUpWithEmail = { onDone() },
                        onClickSignIn = onClickSignIn
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
        onClickSignIn = {
            signInState = SignInState.Init
        },
        onClickSignUp = {
            signInState = SignInState.SignUp
        },
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