package com.viplearner.feature.single_note.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.viplearner.common.presentation.util.ParcelableType
import com.viplearner.common.presentation.util.extension.getSafeParcelable
import com.viplearner.feature.single_note.presentation.model.SingleNoteItem
import com.viplearner.feature.single_note.presentation.viewmodel.SingleNoteViewModel

object SingleNoteNavigation {
    internal const val singleNoteEntityArg = "singleNoteEntityArg"
    const val route = "singleNote"
    internal const val routeWithArgs = "$route/{$singleNoteEntityArg}"
}

fun NavGraphBuilder.singleNoteRoute(
    factory: SingleNoteViewModel.Factory,
    onBackClick: () -> Unit
) = composable(
        route = SingleNoteNavigation.routeWithArgs,
        arguments = listOf(
            navArgument(SingleNoteNavigation.singleNoteEntityArg) {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            slideInHorizontally(initialOffsetX = {it})
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = {it})
        }
    ) {
        it.arguments?.getString(SingleNoteNavigation.singleNoteEntityArg)
        ?.let { noteUUID ->
            SingleNoteRoute(
                factory = factory,
                noteUUID = noteUUID,
                onBackClick = onBackClick
            )
        }
    }