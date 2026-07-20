package com.example.ch3mxr.ui.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ch3mxr.base.GoogleAuthManager
import com.example.ch3mxr.base.SessionManager

fun NavGraphBuilder.appGraph(
    navController: NavHostController
) {
    navigation<Graph.App>(
        startDestination = Routes.Loading
    ) {
        composable<Routes.Loading> {
            val context = LocalContext.current
            val sessionManager = remember { SessionManager(context) }
            val googleAuthManager = remember {
                GoogleAuthManager(context)
            }

            val isLoggedIn by sessionManager.isLoggedIn.collectAsState(initial = null)

            LaunchedEffect(isLoggedIn) {
                if (isLoggedIn == null) return@LaunchedEffect

                if (isLoggedIn == true) {
                    navController.navigate(Graph.Main) {
                        popUpTo(Graph.App) {
                            inclusive = true
                        }
                    }
                } else {
                    val credential = googleAuthManager.tryAutoSignIn()
                    if (credential != null) {
                        navController.navigate(Graph.Main) {
                            popUpTo(Graph.App) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(Graph.Auth) {
                            popUpTo(Graph.App) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}
