package com.example.ch3mxr.ui.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ch3mxr.ui.features.auth.LoginScreenImproved
import com.example.ch3mxr.ui.features.auth.RegisterScreen
import com.example.ch3mxr.ui.features.SessionViewModel

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    sessionViewModel: SessionViewModel
) {
    navigation<Graph.Auth>(
        startDestination = Routes.Login
    ) {
        composable<Routes.Login> {
            LoginScreenImproved(
                sessionViewModel = sessionViewModel,
                googleAuthManager = sessionViewModel.googleAuthManager,
                facebookAuthManager = sessionViewModel.facebookAuthManager,
                onLoginSuccess = { sessionData ->
                    sessionViewModel.saveSession(sessionData)
                    navController.navigate(Graph.Main) {
                        popUpTo(Graph.Auth) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.Register)
                }
            )
        }
        composable<Routes.Register> {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}
