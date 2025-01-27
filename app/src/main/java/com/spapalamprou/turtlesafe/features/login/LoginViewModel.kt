package com.spapalamprou.turtlesafe.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spapalamprou.turtlesafe.domain.useCases.LoginUseCase
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for handling the login state.
 *
 * @property validator An instance of Validator used for validating user input.
 * @property useCase An instance of LoginUseCase used for processing login requests.
 * @property state A MutableStateFlow representing the current state of the login screen.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    val validator: Validator,
    val useCase: LoginUseCase
) : ViewModel() {

    val state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())

    /**
     * Handles user events related to the login screen.
     *
     * @param event The event triggered by user actions in the login screen.
     */
    fun sendEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                state.update { previous ->
                    previous.copy(
                        email = event.email,
                        invalidEmailMessage = validator.validateEmail(event.email).message
                    )
                }
            }

            is LoginEvent.PasswordChanged -> {
                state.update { previous ->
                    previous.copy(
                        password = event.password,
                        invalidPasswordMessage = validator.validateNonEmptyField(event.password).message
                    )
                }
            }

            LoginEvent.LoginButtonClicked ->
                viewModelScope.launch {
                    try {
                        useCase.submit(state.value.transformData())
                    } catch (exception: Exception) {
                        state.update { previous -> previous.copy(exception = exception) }
                    }
                }
        }
    }
}