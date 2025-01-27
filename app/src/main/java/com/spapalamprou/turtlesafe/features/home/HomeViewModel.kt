package com.spapalamprou.turtlesafe.features.home

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.domain.useCases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for managing the state of the home screen.
 *
 * @property useCase An instance of LoginUseCase for managing login.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
   private val useCase: LoginUseCase
) : ViewModel() {

    /**
     * Sends a user event to be processed.
     *
     * @param event The HomeEvent representing a user action on the home screen.
     */
    fun sendEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LogoutButtonClicked -> useCase.logout()
        }
    }
}