package com.viplearner.common.presentation.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.NotesTheme
import com.viplearner.common.presentation.R

@Composable
fun SignInButtons(
    modifier: Modifier = Modifier,
    onSignInWithGoogle: () -> Unit,
    onSignInWithEmail: () -> Unit,
    onClickSignUp: () -> Unit
) {
    Column(modifier = modifier) {
        SignInCard(
            modifier = Modifier
                .height(55.dp),
            icon = Icons.Filled.Email,
            text = "Email"
        ) {
            onSignInWithEmail()
        }
        Spacer(modifier = Modifier.height(15.dp))
        SignInCard(
            modifier = Modifier.height(55.dp),
            icon = ImageVector.vectorResource(R.drawable.icons8_google),
            text = "Sign in with Google",
            color = Color.White,
        ) {
            onSignInWithGoogle()
        }
        val annotatedString = buildAnnotatedString {
            append("Don't have an account? ")
            pushStringAnnotation(
                tag = "SignUp",// provide tag which will then be provided when you click the text
                annotation = "SignUp"
            )
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
                append("Sign up")
            }
            pop()
        }
        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 15.dp).align(
                Alignment.CenterHorizontally),
            onClick = { offset ->
                annotatedString.getStringAnnotations(
                    tag = "SignUp",// tag which you used in the buildAnnotatedString
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    onClickSignUp()
                }
            }
        )
    }
}

@Composable
fun SignUpButtons(
    modifier: Modifier = Modifier,
    onSignUpWithGoogle: () -> Unit,
    onSignUpWithEmail: () -> Unit,
    onClickSignIn: () -> Unit
) {
    Column(modifier = modifier) {
        SignInCard(
            modifier = Modifier.height(55.dp),
            icon = Icons.Filled.Email,
            text = "Sign Up with Email"
        ) {
            onSignUpWithEmail()
        }
        Spacer(modifier = Modifier.height(15.dp))
        SignInCard(
            modifier = Modifier
                .height(45.dp),
            icon = ImageVector.vectorResource(R.drawable.icons8_google),
            text = "Google",
            color = Color.White
        ) {
            onSignUpWithGoogle()
        }
        val annotatedString = buildAnnotatedString {
            append("Already have an account? ")
            pushStringAnnotation(
                tag = "SignIn",// provide tag which will then be provided when you click the text
                annotation = "SignIn"
            )
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
                append("Sign in")
            }
            pop()
        }
        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 15.dp).align(
                Alignment.CenterHorizontally),
            onClick = { offset ->
                annotatedString.getStringAnnotations(
                    tag = "SignIn",// tag which you used in the buildAnnotatedString
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    onClickSignIn()
                }
            }
        )
    }
}

@Preview
@Composable
fun SignInButtonsPreview() {
    val toast = Toast.makeText(LocalContext.current, "Vibes", Toast.LENGTH_SHORT)
    NotesTheme{
        SignInButtons(
            modifier = Modifier.padding(20.dp),
            onSignInWithGoogle = {},
            onSignInWithEmail = {},
            onClickSignUp = {
                toast.show()
            }
        )
    }
}