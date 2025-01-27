package com.spapalamprou.turtlesafe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilledButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        enabled = enabled
    ) {
        Text(
            text = buttonText,
            color = Color.White
        )
    }
}

@Composable
fun RoundButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    imageVector: ImageVector,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(42.dp),
        colors = IconButtonDefaults.iconButtonColors(containerColor = color)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun LabelRoundButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    subText: String = "",
    buttonIcon: ImageVector = Icons.Outlined.Add
) {
    Row(modifier = modifier) {

        Column(
            modifier = Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme
                    .typography
                    .labelLarge
                    .copy(fontSize = 18.sp),
                modifier = Modifier.testTag("labelRoundButtonText")
            )

            if (subText.isNotEmpty()) {
                Text(
                    text = subText,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme
                        .typography
                        .labelLarge
                        .copy(fontSize = 14.sp),
                    modifier = Modifier.testTag("labelRoundButtonSubtext")
                )
            }
        }
        RoundButton(
            onClick = onClick,
            imageVector = buttonIcon,
            contentDescription = "Icon showing the addition sign"
        )
    }
}