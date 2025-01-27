package com.spapalamprou.turtlesafe.features.authentication

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.domain.useCases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * ViewModel responsible for managing the authentication state of the user.
 *
 * @property loginUseCase An instance of LoginUseCase to handle login.
 * @property state A MutableStateFlow that holds the current login status of the user.
 */
@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val state: MutableStateFlow<Boolean> = loginUseCase.isUserLoggedIn
}