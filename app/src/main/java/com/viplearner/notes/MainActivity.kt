package com.viplearner.notes

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.compose.NotesTheme
import com.google.android.gms.auth.api.identity.Identity
import com.viplearner.common.data.local.datastore.NotesDataStoreRepository
import com.viplearner.common.domain.entity.UserData
import com.viplearner.feature.home.presentation.component.sign_in.GoogleAuthUiClient
import com.viplearner.feature.single_note.presentation.viewmodel.SingleNoteViewModel
import com.viplearner.navigation.Navigation
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.launch
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
            NotesTheme {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                notesDataStoreRepository.saveUserData(Json.encodeToString(signInResult.data))
                            }
                        }
                    }
                )
                // A surface container using the 'background' color from the theme
                Navigation(
                    singleNoteViewModelFactory = EntryPointAccessors.fromActivity(
                        this, ViewModelFactoryProvider::class.java)
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
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesTheme {
        Greeting("Android")
    }
}