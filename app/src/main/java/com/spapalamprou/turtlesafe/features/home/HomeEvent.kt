package com.spapalamprou.turtlesafe.features.home

/**
 * Sealed class representing user events for the home screen.
 */
sealed class HomeEvent {

    /**
     * Event triggered when the logout button is clicked.
     */
    data object LogoutButtonClicked : HomeEvent()
}