package com.example.ch3mxr.ui.routes

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ch3mxr.base.GoogleAuthManager
import com.example.ch3mxr.ui.features.auth.LoginScreenImproved
import com.example.ch3mxr.ui.features.auth.RegisterScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<Graph.Auth>(
        startDestination = Routes.Login
    ) {
        composable<Routes.Login> {
            val context = LocalContext.current
            val googleAuthManager = remember {
                GoogleAuthManager(context)
            }
            LoginScreenImproved(
                googleAuthManager = googleAuthManager,
                onLoginSuccess = {
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