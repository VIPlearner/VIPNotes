package com.viplearner.feature.home.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object HomeNavigation {
    const val route = "home"
}

fun NavGraphBuilder.homeRoute(
    navigateToNote: (String) -> Unit,
    navigateToSettings: () -> Unit,
    onSignInViaGoogleClick: () -> Unit,
    onSignInViaFacebookClick: () -> Unit
) {
    composable(HomeNavigation.route) {
        HomeRoute(
            onAddNoteClicked = navigateToNote,
            onNavigateToNote = navigateToNote,
            onNavigateToSettings = navigateToSettings,
            onSignInViaGoogleClick = onSignInViaGoogleClick,
            onSignInViaFacebookClick = onSignInViaFacebookClick
        )
    }
}
