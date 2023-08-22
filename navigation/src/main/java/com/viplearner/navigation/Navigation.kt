package com.viplearner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.viplearner.feature.home.presentation.HomeNavigation
import com.viplearner.feature.home.presentation.homeRoute
import com.viplearner.feature.single_note.presentation.singleNoteRoute
import com.viplearner.feature.single_note.presentation.viewmodel.SingleNoteViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    singleNoteViewModelFactory: SingleNoteViewModel.Factory
) {
    NavHost(
        navController = navController,
        startDestination = HomeNavigation.route
    ) {
        homeRoute(
            navigateToNote = { homeListItem ->
//                val detailEntityArg = homeListItem.toDetailItem().parcelableString()
//                navController.navigate("${DetailNavigation.route}/$detailEntityArg")
            }
        )
        singleNoteRoute(
            factory = singleNoteViewModelFactory
        ) {
            navController.popBackStack()
        }
    }
}