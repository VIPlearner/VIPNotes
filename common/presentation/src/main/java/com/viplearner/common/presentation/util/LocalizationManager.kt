package com.viplearner.common.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.eemmez.localization.LocalizationManager

@Composable
public fun rememberLocalizationManager(): LocalizationManager {
    val context = LocalContext.current
    return remember {
        LocalizationManager(context)
    }
}
