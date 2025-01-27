package com.spapalamprou.turtlesafe.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun PasswordTextField(
    password: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    isError: Boolean = false,
    errorMessage: String = ""
) {
    var passwordVisible: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        isError = isError,
        supportingText = {
            Text(
                text = errorMessage,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        singleLine = true,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        modifier = modifier.testTag("passwordTextfield"),
        label = { Text(text = label) },
        trailingIcon = {
            if (password.isNotEmpty()) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    if (!passwordVisible) {
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = "icon to see password",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.VisibilityOff,
                            contentDescription = "icon to hide password",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF8A858E),
            focusedLabelColor = Color(0xFF8A858E)
        )
    )
}

@Composable
fun DefaultTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    label: String,
    onIconClick: () -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = "",
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = singleLine,
        readOnly = readOnly,
        isError = isError,
        supportingText = {
            Text(
                text = errorMessage,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.testTag("supportingText")
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        modifier = modifier,
        label = {
            Text(
                text = label,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        trailingIcon = {
            if (text.isNotEmpty() && !readOnly) {
                IconButton(
                    onClick = onIconClick,
                    modifier = Modifier.testTag("defaultTextfieldIcon")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "icon to clear input",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF8A858E),
            focusedLabelColor = Color(0xFF8A858E)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String = "",
    enabledDropdown: Boolean = true
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabledDropdown) expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            isError = isError,
            supportingText = {
                Text(
                    text = errorMessage,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            singleLine = true,
            label = {
                Text(
                    text = label,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF8A858E),
                focusedLabelColor = Color(0xFF8A858E)
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}

@Composable
fun IconTextField(
    text: String,
    label: String,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    singleLine: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = "",
    imageVector: ImageVector,
    contentDescription: String,
    trailingIconTestTag: String = "",
) {

    OutlinedTextField(
        value = text,
        onValueChange = {},
        singleLine = singleLine,
        modifier = modifier,
        label = { Text(text = label) },
        isError = isError,
        supportingText = {
            Text(
                text = errorMessage,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onIconClick,
                modifier = Modifier.testTag(trailingIconTestTag)
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF8A858E),
            focusedLabelColor = Color(0xFF8A858E)
        )
    )
}
