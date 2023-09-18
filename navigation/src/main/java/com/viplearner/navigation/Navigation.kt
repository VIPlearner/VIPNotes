package com.viplearner.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.viplearner.feature.home.presentation.HomeNavigation
import com.viplearner.feature.home.presentation.homeRoute
import com.viplearner.feature.single_note.presentation.SingleNoteNavigation
import com.viplearner.feature.single_note.presentation.singleNoteRoute
import com.viplearner.feature.single_note.presentation.viewmodel.SingleNoteViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    singleNoteViewModelFactory: SingleNoteViewModel.Factory,
    onSignInViaGoogleClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = HomeNavigation.route,
    ) {
        homeRoute(
            navigateToNote = { uuid ->
                val detailEntityArg = uuid
                navController.navigate("${SingleNoteNavigation.route}/$detailEntityArg")
            },
            onSignInViaGoogleClick = onSignInViaGoogleClick
        )
        singleNoteRoute(
            factory = singleNoteViewModelFactory
        ) {
            navController.popBackStack()
        }
    }
}