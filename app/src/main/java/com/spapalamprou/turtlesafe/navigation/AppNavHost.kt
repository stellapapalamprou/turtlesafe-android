package com.spapalamprou.turtlesafe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyViewModel
import com.spapalamprou.turtlesafe.screens.ForgotPasswordScreen
import com.spapalamprou.turtlesafe.screens.HomeScreen
import com.spapalamprou.turtlesafe.screens.LandingScreen
import com.spapalamprou.turtlesafe.screens.LoginScreen
import com.spapalamprou.turtlesafe.screens.MorningSurveyScreen
import com.spapalamprou.turtlesafe.screens.NestExcavationScreen
import com.spapalamprou.turtlesafe.screens.NestHatchingScreen
import com.spapalamprou.turtlesafe.screens.NestIncubationScreen
import com.spapalamprou.turtlesafe.screens.NestRelocationScreen
import com.spapalamprou.turtlesafe.screens.NewNestScreen
import com.spapalamprou.turtlesafe.screens.NightSurveyScreen
import com.spapalamprou.turtlesafe.screens.ResetPasswordScreen
import com.spapalamprou.turtlesafe.screens.SignUpScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(startDestination = "home", route = "mainApp") {
            composable("home") {
                HomeScreen(
                    navigateToMorningSurvey = { navController.navigate("morningSurveyFlow") },
                    navigateToNightSurvey = { navController.navigate("nightSurvey") }
                )
            }


            navigation(startDestination = "morningSurvey", route = "morningSurveyFlow") {
                composable("morningSurvey") { entry ->
                    val subgraphBackStackEntry = remember(entry) {
                        navController.getBackStackEntry("morningSurveyFlow")
                    }

                    val sharedViewModel: MorningSurveyViewModel =
                        hiltViewModel(subgraphBackStackEntry)

                    MorningSurveyScreen(
                        sharedViewModel,
                        navigateBack = { navController.popBackStack() },
                        navigateToNewNest = { navController.navigate("newNest") },
                        navigateToNestRelocation = { navController.navigate("nestRelocation") },
                        navigateToNestIncubation = { navController.navigate("nestIncubation") },
                        navigateToNestExcavation = { navController.navigate("nestExcavation") },
                        navigateToNestHatching = { navController.navigate("nestHatching") }
                    )

                }
                composable("newNest") { entry ->
                    val subgraphBackStackEntry = remember(entry) {
                        navController.getBackStackEntry("morningSurveyFlow")
                    }

                    val sharedViewModel: MorningSurveyViewModel =
                        hiltViewModel(subgraphBackStackEntry)

                    NewNestScreen(
                        sharedViewModel,
                        navigateBack = { navController.popBackStack() }
                    )
                }
                composable("nestRelocation") { entry ->
                    val subgraphBackStackEntry = remember(entry) {
                        navController.getBackStackEntry("morningSurveyFlow")
                    }

                    val sharedViewModel: MorningSurveyViewModel =
                        hiltViewModel(subgraphBackStackEntry)

                    NestRelocationScreen(
                        sharedViewModel,
                        navigateBack = { navController.popBackStack() }
                    )
                }
                composable("nestIncubation") { entry ->
                    val subgraphBackStackEntry = remember(entry) {
                        navController.getBackStackEntry("morningSurveyFlow")
                    }

                    val sharedViewModel: MorningSurveyViewModel =
                        hiltViewModel(subgraphBackStackEntry)

                    NestIncubationScreen(
                        sharedViewModel,
                        navigateBack = { navController.popBackStack() }
                    )
                }
                composable("nestExcavation") { entry ->
                    val subgraphBackStackEntry = remember(entry) {
                        navController.getBackStackEntry("morningSurveyFlow")
                    }

                    val sharedViewModel: MorningSurveyViewModel =
                        hiltViewModel(subgraphBackStackEntry)

                    NestExcavationScreen(
                        sharedViewModel,
                        navigateBack = { navController.popBackStack() }
                    )
                }
                composable("nestHatching") {entry ->
                    val subgraphBackStackEntry = remember(entry) {
                        navController.getBackStackEntry("morningSurveyFlow")
                    }

                    val sharedViewModel: MorningSurveyViewModel =
                        hiltViewModel(subgraphBackStackEntry)

                    NestHatchingScreen(
                        sharedViewModel,
                        navigateBack = { navController.popBackStack() }
                    )
                }
            }
            composable("nightSurvey") {
                NightSurveyScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
        }

        navigation(startDestination = "landing", route = "userAuth") {
            composable("landing") {
                LandingScreen(
                    navigateToLogin = { navController.navigate("login") },
                    navigateToSignUp = { navController.navigate("signUp") }
                )
            }
            composable("login") {
                LoginScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToForgotPassword = { navController.navigate("forgotPassword") },
                    navigateToSignUp = { navController.navigate("signUp") }
                )
            }
            composable("signUp") {
                SignUpScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
            composable("forgotPassword") {
                ForgotPasswordScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToResetPassword = { navController.navigate("resetPassword") }
                )
            }
            composable("resetPassword") {
                ResetPasswordScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}