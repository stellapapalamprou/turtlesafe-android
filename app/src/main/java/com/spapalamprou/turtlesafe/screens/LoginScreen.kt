package com.spapalamprou.turtlesafe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.R
import com.spapalamprou.turtlesafe.features.login.LoginEvent
import com.spapalamprou.turtlesafe.features.login.LoginViewModel
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.components.PasswordTextField
import com.spapalamprou.turtlesafe.ui.theme.TurtleSafeTheme
import com.spapalamprou.turtlesafe.validators.isEmail


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateBack: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    val loginSnackbarState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Login",
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
        snackbarHost = { SnackbarHost(loginSnackbarState) },

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
                            painter = painterResource(id = R.drawable.logo_turtlesafe_turtle_color),
                            contentDescription = "image of TurtleSafe logo",
                            modifier = Modifier
                                .width(122.dp)
                                .height(122.dp)
                                .align(alignment = Alignment.Center)
                                .testTag("loginScreen")
                        )
                    }

                    DefaultTextField(
                        text = state.value.email,
                        onValueChange = { input ->
                            viewModel.sendEvent(LoginEvent.EmailChanged(input))
                        },
                        label = "Email",
                        isError = state.value.invalidEmailMessage.isNotEmpty(),
                        errorMessage = state.value.invalidEmailMessage,
                        keyboardType = KeyboardType.Email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .testTag("email"),
                        onIconClick = { viewModel.sendEvent(LoginEvent.EmailChanged("")) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PasswordTextField(
                        password = state.value.password,
                        isError = state.value.invalidPasswordMessage.isNotEmpty(),
                        errorMessage = state.value.invalidPasswordMessage,
                        onValueChange = { input ->
                            viewModel.sendEvent(LoginEvent.PasswordChanged(input))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .testTag("password"),

                        )

                    TextButton(
                        onClick = { navigateToForgotPassword() },
                        modifier = Modifier
                            .padding(end = 24.dp)
                            .align(alignment = Alignment.End)
                    ) {
                        Text(
                            text = "Forgot Password?",
                            color = MaterialTheme.colorScheme.onSurface
                        )

                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    FilledButton(
                        buttonText = "Login",
                        onClick = { viewModel.sendEvent(LoginEvent.LoginButtonClicked) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .testTag("submitButton"),
                        enabled = state.value.email.isEmail() &&
                                state.value.password.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                }
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)

                    )
                    TextButton(onClick = { navigateToSignUp() }) {
                        Text(
                            text = "Sign Up",
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    LaunchedEffect(state.value.exception) {
                        if (state.value.exception != null) {
                            loginSnackbarState.showSnackbar("Login failed")
                        }
                    }
                }
            }
        }
    )
}

@Composable
@Preview
fun LoginScreenPreview() {
    TurtleSafeTheme {
        LoginScreen({}, {}, {})
    }
}