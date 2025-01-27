package com.spapalamprou.turtlesafe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.R
import com.spapalamprou.turtlesafe.features.resetPassword.ResetPasswordEvent
import com.spapalamprou.turtlesafe.features.resetPassword.ResetPasswordViewModel
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.components.PasswordTextField
import com.spapalamprou.turtlesafe.ui.theme.TurtleSafeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    navigateBack: () -> Unit
) {
    val viewModel: ResetPasswordViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Reset Password",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "arrow to navigate back",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        )
    },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier) {
                    Box(
                        modifier = Modifier
                            .height(260.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.key),
                            contentDescription = "image of a key",
                            modifier = Modifier
                                .width(85.dp)
                                .height(85.dp)
                                .align(alignment = Alignment.Center)
                        )
                    }
                    Text(
                        text = "New Password",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Enter the User ID and the token provided in the email you received",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DefaultTextField(
                        text = state.value.userId,
                        onValueChange = { input ->
                            viewModel.sendEvent(ResetPasswordEvent.UserIdChanged(input))
                        },
                        isError = state.value.invalidUserIdMessage.isNotEmpty(),
                        errorMessage = state.value.invalidUserIdMessage,
                        label = "User ID",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onIconClick = { viewModel.sendEvent(ResetPasswordEvent.UserIdChanged("")) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.token,
                        onValueChange = { input ->
                            viewModel.sendEvent(ResetPasswordEvent.TokenChanged(input))
                        },
                        isError = state.value.invalidTokenMessage.isNotEmpty(),
                        errorMessage = state.value.invalidTokenMessage,
                        label = "Token",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onIconClick = { viewModel.sendEvent(ResetPasswordEvent.TokenChanged("")) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PasswordTextField(
                        password = state.value.password,
                        onValueChange = { input ->
                            viewModel.sendEvent(ResetPasswordEvent.PasswordChanged(input))
                        },
                        isError = state.value.invalidPasswordMessage.isNotEmpty(),
                        errorMessage = state.value.invalidPasswordMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "New Password"
                    )

                    Spacer(modifier = Modifier.height(52.dp))

                    FilledButton(
                        buttonText = "Submit",
                        onClick = { viewModel.sendEvent(ResetPasswordEvent.SubmitButtonClicked) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    )
}

@Composable
@Preview
fun ResetPasswordScreenPreview() {
    TurtleSafeTheme {
        ResetPasswordScreen({})
    }
}