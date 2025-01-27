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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.R
import com.spapalamprou.turtlesafe.features.forgotPassword.ForgotPasswordEvent
import com.spapalamprou.turtlesafe.features.forgotPassword.ForgotPasswordViewModel
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.theme.TurtleSafeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navigateBack: () -> Unit,
    navigateToResetPassword: () -> Unit
) {
    val viewModel: ForgotPasswordViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Forgot Password",
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
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = "image of TurtleSafe logo",
                            modifier = Modifier
                                .width(73.dp)
                                .height(85.dp)
                                .align(alignment = Alignment.Center)
                        )
                    }
                    Text(
                        text = "Forgot Password?",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Enter your email address to receive a code for password reset",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DefaultTextField(
                        text = state.value.email,
                        onValueChange = { input ->
                            viewModel.sendEvent(ForgotPasswordEvent.EmailChanged(input))
                        },
                        isError = state.value.invalidEmailMessage.isNotEmpty(),
                        errorMessage = state.value.invalidEmailMessage,
                        label = "Email",
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onIconClick = { viewModel.sendEvent(ForgotPasswordEvent.EmailChanged("")) }
                    )

                    Spacer(modifier = Modifier.height(52.dp))

                    FilledButton(
                        buttonText = "Send Code",
                        onClick = {
                            viewModel.sendEvent(ForgotPasswordEvent.SendCodeButtonClicked)
                            navigateToResetPassword()
                        },
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
fun ForgotPasswordScreenPreview() {
    TurtleSafeTheme {
        ForgotPasswordScreen({}, {})
    }
}