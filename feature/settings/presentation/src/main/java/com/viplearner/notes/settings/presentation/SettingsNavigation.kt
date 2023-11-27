package com.viplearner.notes.settings.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object SettingsNavigation {
    const val route = "settings"
}

fun NavGraphBuilder.settingsRoute(
    onBackClicked: () -> Unit,
) {
    composable(SettingsNavigation.route) {
        SettingsRoute(
            onBackClicked = onBackClicked
        )
    }
}