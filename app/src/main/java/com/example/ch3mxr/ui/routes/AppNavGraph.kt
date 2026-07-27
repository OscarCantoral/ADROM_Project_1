package com.example.ch3mxr.ui.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ch3mxr.base.SessionData
import com.example.ch3mxr.ui.features.splash.SessionViewModel

fun NavGraphBuilder.appGraph(
    navController: NavHostController,
    sessionViewModel: SessionViewModel
) {
    navigation<Graph.App>(
        startDestination = Routes.Loading
    ) {
        composable<Routes.Loading> {
            val session: SessionData? by sessionViewModel.localData.getSessionData.collectAsStateWithLifecycle(
                initialValue = null
            )

            LaunchedEffect(session) {
                if (session == null) {
                    navController.navigate(Graph.Auth) {
                        popUpTo(Graph.App) {
                            inclusive = true
                        }
                    }
                } else {
                    navController.navigate(Graph.Main) {
                        popUpTo(Graph.App) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}
