package com.spapalamprou.turtlesafe.features.resetPassword

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state of reset password screen.
 *
 * @property validator An instance of Validator used to validate user input.
 * @property state A MutableStateFlow representing the current state of the reset password screen.
 */
@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    val validator: Validator
) : ViewModel() {
    val state: MutableStateFlow<ResetPasswordState> = MutableStateFlow(ResetPasswordState())

    /**
     * Processes user input events related to the reset password screen.
     *
     * @param event The ResetPasswordEvent representing the user interaction.
     */
    fun sendEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.UserIdChanged -> state.update { previous ->
                previous.copy(
                    userId = event.userId,
                    invalidUserIdMessage = validator.validateNonEmptyField(event.userId).message
                )
            }

            is ResetPasswordEvent.TokenChanged -> state.update { previous ->
                previous.copy(
                    token = event.token,
                    invalidTokenMessage = validator.validateNonEmptyField(event.token).message
                )
            }

            is ResetPasswordEvent.PasswordChanged -> state.update { previous ->
                previous.copy(
                    password = event.password,
                    invalidPasswordMessage = validator.validatePassword(event.password).message
                )
            }

            ResetPasswordEvent.SubmitButtonClicked -> TODO()
        }
    }
}