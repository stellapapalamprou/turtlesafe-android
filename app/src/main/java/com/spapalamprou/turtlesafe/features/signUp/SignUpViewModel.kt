package com.spapalamprou.turtlesafe.features.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spapalamprou.turtlesafe.domain.useCases.LoginUseCase
import com.spapalamprou.turtlesafe.domain.useCases.SignUpUseCase
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state of the sign up screen.
 *
 * @property validator An instance of Validator used for validating user inputs.
 * @property signUpUseCase An instance of SignUpUseCase for handling sign up.
 * @property loginUseCase An instance of LoginUseCase for handling login.
 * @property state A MutableStateFlow representing the current state of the sign up screen.
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validator: Validator,
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase

) : ViewModel() {
    val state: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())

    /**
     * Handles events related to the sign up screen.
     *
     * @param event The SignUpEvent to be processed.
     */
    fun sendEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> {
                state.update { previous ->
                    previous.copy(
                        email = event.email,
                        invalidEmailMessage = validator.validateEmail(event.email).message
                    )
                }
            }

            is SignUpEvent.FirstNameChanged -> {
                state.update { previous ->
                    previous.copy(
                        firstName = event.firstName,
                        invalidFirstNameMessage = validator.validateName(event.firstName).message
                    )
                }

            }

            is SignUpEvent.LastNameChanged -> {
                state.update { previous ->
                    previous.copy(
                        lastName = event.lastName,
                        invalidLastNameMessage = validator.validateName(event.lastName).message
                    )
                }
            }

            is SignUpEvent.PasswordChanged -> {
                state.update { previous ->
                    previous.copy(
                        password = event.password,
                        invalidPasswordMessage = validator.validatePassword(event.password).message
                    )
                }
            }

            SignUpEvent.SignUpButtonClicked ->
                viewModelScope.launch {
                    try {
                        signUpUseCase.submit(state.value.transformData())
                        loginUseCase.submit(state.value.transformDataToLogin())
                    } catch (exception: Exception) {
                        state.update { previous -> previous.copy(exception = exception) }
                    }
                }
        }
    }
}