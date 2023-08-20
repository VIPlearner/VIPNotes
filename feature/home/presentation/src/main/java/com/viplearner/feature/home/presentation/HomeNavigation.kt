package com.viplearner.feature.home.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.viplearner.feature.home.presentation.model.NoteItem

object HomeNavigation {
    const val route = "home"
}

fun NavGraphBuilder.homeRoute(navigateToNote: (NoteItem) -> Unit) {
    composable(HomeNavigation.route) {
        HomeRoute(
            onItemClick = {
                navigateToNote.invoke(it)
            }
        )
    }
}
