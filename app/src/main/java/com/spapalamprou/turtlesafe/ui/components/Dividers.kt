package com.spapalamprou.turtlesafe.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextDivider(
    modifier: Modifier = Modifier,
    text: String
) {

    Row(modifier = modifier) {

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically)
        )

        Text(
            text = text,
            modifier = Modifier
                .testTag("dividerHasText")
                .padding(horizontal = 24.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme
                .typography
                .labelLarge
                .copy(fontSize = 14.sp)
        )

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically)
        )
    }
}