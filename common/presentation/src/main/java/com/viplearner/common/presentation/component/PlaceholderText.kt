package com.viplearner.common.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PlaceholderText(value: String){
    Text(
        text = value,
        style = MaterialTheme.typography.bodyLarge,
    )
}