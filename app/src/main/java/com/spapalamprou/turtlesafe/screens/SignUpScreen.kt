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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.R
import com.spapalamprou.turtlesafe.features.signUp.SignUpEvent
import com.spapalamprou.turtlesafe.features.signUp.SignUpViewModel
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.components.PasswordTextField
import com.spapalamprou.turtlesafe.ui.theme.TurtleSafeTheme
import com.spapalamprou.turtlesafe.validators.isEmail
import com.spapalamprou.turtlesafe.validators.isName
import com.spapalamprou.turtlesafe.validators.isPassword

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigateBack: () -> Unit
) {
    val viewModel: SignUpViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    val signUpSnackbarState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Sign Up",
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
        snackbarHost = { SnackbarHost(signUpSnackbarState) },

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
                        )
                    }

                    DefaultTextField(
                        text = state.value.firstName,
                        onValueChange = { input ->
                            viewModel.sendEvent(SignUpEvent.FirstNameChanged(input))
                        },
                        isError = state.value.invalidFirstNameMessage.isNotEmpty(),
                        errorMessage = state.value.invalidFirstNameMessage,
                        label = "First Name",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onIconClick = { viewModel.sendEvent(SignUpEvent.FirstNameChanged("")) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.lastName,
                        onValueChange = { input ->
                            viewModel.sendEvent(SignUpEvent.LastNameChanged(input))
                        },
                        isError = state.value.invalidLastNameMessage.isNotEmpty(),
                        errorMessage = state.value.invalidLastNameMessage,
                        label = "Last Name",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onIconClick = { viewModel.sendEvent(SignUpEvent.LastNameChanged("")) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.email,
                        onValueChange = { input ->
                            viewModel.sendEvent(SignUpEvent.EmailChanged(input))
                        },
                        isError = state.value.invalidEmailMessage.isNotEmpty(),
                        errorMessage = state.value.invalidEmailMessage,
                        label = "Email",
                        keyboardType = KeyboardType.Email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onIconClick = { viewModel.sendEvent(SignUpEvent.EmailChanged("")) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PasswordTextField(
                        password = state.value.password,
                        onValueChange = { input ->
                            viewModel.sendEvent(SignUpEvent.PasswordChanged(input))
                        },
                        isError = state.value.invalidPasswordMessage.isNotEmpty(),
                        errorMessage = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    if (state.value.password.isNotEmpty()) {
                        Text(
                            text = "• Be at least 8 characters long\n• Include at least 1 number",
                            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(52.dp))

                    FilledButton(
                        buttonText = "Sign Up",
                        onClick = { viewModel.sendEvent(SignUpEvent.SignUpButtonClicked) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        enabled = state.value.firstName.isName() &&
                                state.value.lastName.isName() &&
                                state.value.email.isEmail() &&
                                state.value.password.isPassword()

                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    LaunchedEffect(state.value.exception) {
                        if (state.value.exception != null) {
                            signUpSnackbarState.showSnackbar("Sign up failed")
                        }
                    }
                }
            }
        }
    )
}

@Composable
@Preview
fun SignUpScreenPreview() {
    TurtleSafeTheme {
        SignUpScreen({})
    }
}