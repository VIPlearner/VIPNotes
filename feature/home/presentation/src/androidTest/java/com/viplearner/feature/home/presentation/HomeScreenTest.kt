package com.viplearner.feature.home.presentation

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import com.viplearner.feature.home.presentation.model.NoteItem
import com.viplearner.feature.home.presentation.state.HomeScreenUiEvent
import com.viplearner.feature.home.presentation.state.HomeScreenUiState
import org.junit.Rule
import org.junit.Test
import com.viplearner.common.presentation.R as commonPresentationRes

class HomeScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun isExist_searchBar() {
        composeRule.setContent {
//            HomeScreen(
//                onSearchTextChanged = {},
//                onItemClick = {},
//                onItemLongClick = {},
//                onAddNoteClick = {},
//                onDeleteNoteClicked = {},
//                searchValue = "",
//                homeScreenUiState = HomeScreenUiState.Content.NormalMode(listOf(),false),
//                homeScreenUiEvent = HomeScreenUiEvent.Idle,
//                snackbarHostState = SnackbarHostState()
//            )
        }
        composeRule.onNodeWithTag(HomeTag.searchBox).assertExists()
    }

    @Test
    fun isErrorDialog_displayed_on_error_state() {
        val errorMessage =
            composeRule.activity.getString(commonPresentationRes.string.unknown_error)
        composeRule.setContent {
//            HomeScreen(
//                onSearchTextChanged = {},
//                onItemClick = {},
//                onItemLongClick = {},
//                onAddNoteClick = {},
//                onDeleteNoteClicked = {},
//                searchValue = "",
//                homeScreenUiState = HomeScreenUiState.Error(errorMessage),
//                homeScreenUiEvent = HomeScreenUiEvent.Idle,
//                snackbarHostState = SnackbarHostState()
//            )
        }
        composeRule.onNodeWithText(composeRule.activity.getString(commonPresentationRes.string.unknown_error))
            .assertIsDisplayed()
    }

    @Test
    fun isSnackbar_displayed_when_longClick_list_item() {
        composeRule.setContent {
            var homeScreenUiEvent: HomeScreenUiEvent by remember {
                mutableStateOf(HomeScreenUiEvent.Idle)
            }
//            HomeScreen(
//                onSearchTextChanged = {},
//                onItemClick = {},
//                onAddNoteClick = {},
//                onItemLongClick = {
//                    homeScreenUiEvent = HomeScreenUiEvent.AddNoteSuccess("Added Successfully!")
//                },
//                onDeleteNoteClicked = {},
//                searchValue = "",
//                homeScreenUiState = HomeScreenUiState.Content.NormalMode(
//                    listOf(
//                        NoteItem(
//                            "902930",
//                            "How to make pancakes",
//                            "Whisk the eggs to make pancakes for the house to eat fro the bowl",
//                            1698737373L,
//                            false,
//                            false
//                        )
//                    )
//                ),
//                homeScreenUiEvent = homeScreenUiEvent,
//                snackbarHostState = SnackbarHostState()
//            )
        }

        composeRule.onNodeWithText("kedi1").performTouchInput { longClick() }
        composeRule.waitUntil {
            composeRule.onAllNodesWithTag(HomeTag.snackbar)
                .fetchSemanticsNodes().size == 1
        }
        composeRule.onNodeWithTag(HomeTag.snackbar).assertIsDisplayed()
    }
}