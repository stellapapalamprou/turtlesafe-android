package com.spapalamprou.turtlesafe.features

import com.spapalamprou.turtlesafe.domain.useCases.LoginUseCase
import com.spapalamprou.turtlesafe.features.home.HomeEvent
import com.spapalamprou.turtlesafe.features.home.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private val useCase: LoginUseCase = mockk()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun home_logout() {
        runTest {
            every { useCase.logout() } returns Unit
            viewModel.sendEvent(HomeEvent.LogoutButtonClicked)
            verify { useCase.logout() }
        }
    }
}