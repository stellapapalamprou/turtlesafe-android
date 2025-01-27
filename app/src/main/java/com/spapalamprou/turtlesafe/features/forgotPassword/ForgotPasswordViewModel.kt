package com.spapalamprou.turtlesafe.features.forgotPassword

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel that manages the state of the Forgot Password feature.
 *
 * @property validator An instance of Validator used to validate the email input.
 * @property state A MutableStateFlow representing the current state of the
 * forgot password screen.
 */
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    val validator: Validator
) : ViewModel() {

    val state: MutableStateFlow<ForgotPasswordState> = MutableStateFlow(ForgotPasswordState())

    /**
     * Handles user events related to the Forgot Password process.
     *
     * @param event The ForgotPasswordEvent that represents the user action to be processed.
     */
    fun sendEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChanged ->
                state.update { previous ->
                    previous.copy(
                        email = event.email,
                        invalidEmailMessage = validator.validateEmail(event.email).message
                    )
                }

            ForgotPasswordEvent.SendCodeButtonClicked -> TODO()
        }
    }
}

