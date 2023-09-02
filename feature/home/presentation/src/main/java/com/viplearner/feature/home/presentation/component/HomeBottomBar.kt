package com.viplearner.feature.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viplearner.common.presentation.util.rememberLocalizationManager
import com.viplearner.feature.home.presentation.R

@Composable
fun HomeBottomBar(
    modifier: Modifier,
    allSelectedIsPinned: Boolean,
    isAllDeselected: Boolean,
    onPinItems: () -> Unit,
    onUnpinItems: () -> Unit,
    onDelete: () -> Unit,
) {
    val localizationManager = rememberLocalizationManager()
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
    ) {
        Row(
            modifier = modifier
                .wrapContentSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomBarIcon(
                enabled = !isAllDeselected,
                icon = Icons.Default.PushPin,
                text = if(!allSelectedIsPinned)localizationManager.getString(
                    R.string.pin
                ) else localizationManager.getString(
                    R.string.unpin
                )
            ) {
                if(!allSelectedIsPinned)onPinItems() else onUnpinItems()
            }
            BottomBarIcon(
                enabled = !isAllDeselected,
                icon = Icons.Default.Delete,
                text = localizationManager.getString(
                    R.string.delete
                )
            ) {
                onDelete()
            }
        }
    }
}

@Composable
fun BottomBarIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview(backgroundColor = 0xFF000000, showBackground = true)
@Composable
fun HomeBottomBarPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        HomeBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            allSelectedIsPinned = true,
            isAllDeselected = true,
            onDelete = {},
            onPinItems = {},
            onUnpinItems = {}
        )
    }
}