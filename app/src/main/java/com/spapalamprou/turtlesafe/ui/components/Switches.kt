package com.spapalamprou.turtlesafe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp

@Composable
fun LabelSwitch(
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    subText: String = "",
    switchChecked: Boolean
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
                    .copy(fontSize = 18.sp)
            )

            if (subText.isNotEmpty()) {
                Text(
                    text = subText,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme
                        .typography
                        .labelLarge
                        .copy(fontSize = 14.sp)
                )
            }
        }

        Switch(
            checked = switchChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag("switch"),
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimaryContainer,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainer
            )
        )
    }
}