package com.example.ch3mxr.ui.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ch3mxr.ui.features.SessionViewModel
import com.example.ch3mxr.ui.features.splash.LoadingScreen
import kotlinx.coroutines.delay

fun NavGraphBuilder.appGraph(
    navController: NavHostController,
    sessionViewModel: SessionViewModel
) {

    navigation<Graph.App>(
        startDestination = Routes.Loading
    ) {

        composable<Routes.Loading> {

            LoadingScreen()

            LaunchedEffect(Unit) {

                // Temporal: mostramos Loading y luego vamos al Login
                delay(800)

                navController.navigate(Graph.Auth) {
                    popUpTo(Graph.App) {
                        inclusive = true
                    }
                }
            }
        }
    }
}