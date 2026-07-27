package com.example.ch3mxr.ui.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ch3mxr.base.FacebookAuthManager
import com.example.ch3mxr.base.GoogleAuthManager
import com.example.ch3mxr.base.SessionData
import com.example.ch3mxr.ui.features.auth.LoginScreenImproved
import com.example.ch3mxr.ui.features.auth.RegisterScreen
import com.example.ch3mxr.ui.features.splash.SessionViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.authGraph(navController: NavHostController,sessionViewModel: SessionViewModel) {
    navigation<Graph.Auth>(
        startDestination = Routes.Login
    ) {
        composable<Routes.Login> {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            val googleAuthManager = remember {
                GoogleAuthManager(context)
            }
            val facebookAuthManager = remember {
                FacebookAuthManager().also { it.init() }
            }

            LaunchedEffect(Unit) {
                facebookAuthManager.onLoginSuccess = { token, userId ->
                    val session = SessionData(
                        authProvider = "facebook",
                        token = token,
                        userId = userId
                    )
                    scope.launch {
                        sessionViewModel.saveSession(session)
                    }
                    navController.navigate(Graph.Main) {
                        popUpTo(Graph.Auth) { inclusive = true }
                    }
                }
            }

            LoginScreenImproved(
                googleAuthManager = googleAuthManager,
                facebookAuthManager = facebookAuthManager,
                onLoginSuccess = { sessionData ->
                    scope.launch {
                        sessionViewModel.saveSession(sessionData)
                    }
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
