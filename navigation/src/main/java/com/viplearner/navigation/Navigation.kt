package com.viplearner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.viplearner.feature.home.presentation.HomeNavigation
import com.viplearner.feature.home.presentation.homeRoute
import com.viplearner.feature.single_note.presentation.SingleNoteNavigation
import com.viplearner.feature.single_note.presentation.singleNoteRoute
import com.viplearner.feature.single_note.presentation.viewmodel.SingleNoteViewModel
import com.viplearner.notes.settings.presentation.SettingsNavigation
import com.viplearner.notes.settings.presentation.settingsRoute

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    singleNoteViewModelFactory: SingleNoteViewModel.Factory,
    onSignInViaGoogleClick: () -> Unit,
    onSignInViaFacebookClick: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = HomeNavigation.route,
    ) {
        homeRoute(
            navigateToNote = { uuid ->
                navController.navigate("${SingleNoteNavigation.route}/$uuid")
            },
            navigateToSettings = {
                navController.navigate(SettingsNavigation.route)
            },
            onSignInViaGoogleClick = onSignInViaGoogleClick,
            onSignInViaFacebookClick = onSignInViaFacebookClick
        )
        singleNoteRoute(
            factory = singleNoteViewModelFactory
        ) {
            navController.popBackStack()
        }
        settingsRoute {
            navController.popBackStack()
        }
    }
}