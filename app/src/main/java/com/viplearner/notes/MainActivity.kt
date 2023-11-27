package com.viplearner.notes

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.lifecycleScope
import com.example.compose.NotesTheme
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.viplearner.common.data.remote.auth_repository.GoogleAuthUiClient
import com.viplearner.common.domain.datastore.NotesDataStoreRepository
import com.viplearner.common.domain.entity.UserData
import com.viplearner.feature.single_note.presentation.viewmodel.SingleNoteViewModel
import com.viplearner.navigation.Navigation
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private var callbackManager = CallbackManager.Factory.create();

    @Inject
    lateinit var notesDataStoreRepository: NotesDataStoreRepository

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun singleNoteViewModelFactory(): SingleNoteViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            DisposableEffect(Unit) {
                LoginManager.getInstance().registerCallback(
                    callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            println("onSuccess $loginResult")
                            lifecycleScope.launch {
                                val token = loginResult.accessToken.token
                                val credential = FacebookAuthProvider.getCredential(token)
                                val authResult =
                                    Firebase.auth.signInWithCredential(credential).await()
                                if (authResult.user != null) {
                                    notesDataStoreRepository.saveUserData(
                                        Json.encodeToString<UserData>(
                                            UserData(
                                                authResult.user!!.uid,
                                                authResult.user?.email,
                                                authResult.user?.photoUrl.toString()
                                            )
                                        )
                                    )
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "Unable to sign in with Facebook",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                        override fun onCancel() {
                        }

                        override fun onError(exception: FacebookException) {
                            Toast.makeText(
                                applicationContext,
                                exception.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
                onDispose {
                    LoginManager.getInstance().unregisterCallback(callbackManager)
                }
            }
            NotesTheme {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                notesDataStoreRepository.saveUserData(
                                    Json.encodeToString(
                                        signInResult.data
                                    )
                                )
                            }
                        }
                    }
                )
                // A surface container using the 'background' color from the theme
                Navigation(
                    singleNoteViewModelFactory = EntryPointAccessors.fromActivity(
                        this, ViewModelFactoryProvider::class.java
                    )
                        .singleNoteViewModelFactory(),
                    onSignInViaGoogleClick = {
                        lifecycleScope.launch {
                            val intentSender = googleAuthUiClient.signIn()
                            intentSender?.let { intentSender1 ->
                                launcher.launch(
                                    IntentSenderRequest.Builder(intentSender1).build()
                                )
                            }
                        }
                    },
                    onSignInViaFacebookClick = {
                        LoginManager.getInstance().logInWithReadPermissions(
                            this,
                            listOf("email, public_profile")
                        )
                    }
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}