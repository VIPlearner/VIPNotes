package com.viplearner.feature.home.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ConfirmDialog(
    confirmDialogState: ConfirmDialogState
) {
    if (confirmDialogState.openDialog) {
        AlertDialog(
            icon = { Icon(confirmDialogState.icon, contentDescription = "Example Icon") },
            title = { Text(text = confirmDialogState.dialogTitle) },
            text = { Text(text = confirmDialogState.dialogText) },
            onDismissRequest = { confirmDialogState.onDismissRequest() },
            confirmButton = {
                TextButton(onClick = { confirmDialogState.onConfirmation() }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDialogState.onDismissRequest() }) {
                    Text("Dismiss")
                }
            }
        )
    }
}

data class ConfirmDialogState(
    val openDialog: Boolean,
    val onDismissRequest: () -> Unit,
    val onConfirmation: () -> Unit,
    val dialogTitle: String,
    val dialogText: String,
    val icon: ImageVector
)