package com.spapalamprou.turtlesafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.spapalamprou.turtlesafe.features.authentication.AuthenticationViewModel
import com.spapalamprou.turtlesafe.navigation.AppNavHost
import com.spapalamprou.turtlesafe.ui.theme.TurtleSafeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authenticationViewModel by viewModels<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Thread.sleep(500)
        setContent {
            val state = authenticationViewModel.state.collectAsState()

            TurtleSafeTheme {
                AppNavHost(
                    startDestination = if (state.value) {
                        "mainApp"
                    } else {
                        "userAuth"
                    }
                )
            }
        }
    }
}