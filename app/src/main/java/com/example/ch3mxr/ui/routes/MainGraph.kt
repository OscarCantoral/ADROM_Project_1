package com.example.ch3mxr.ui.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.ch3mxr.ui.features.main.CreateGroupScreen
import com.example.ch3mxr.ui.features.main.EditGroupScreen
import com.example.ch3mxr.ui.features.main.GroupsScreen
import com.example.ch3mxr.ui.features.main.HomeScreen
import com.example.ch3mxr.ui.features.main.QuimicaScreen
import com.example.ch3mxr.ui.features.splash.SessionViewModel

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    viewModel: SessionViewModel
) {

    navigation<Graph.Main>(
        startDestination = Routes.Home
    ) {
        composable<Routes.Home> {
            HomeScreen(
                onQuimicaClick = {
                    navController.navigate(Routes.Quimica)
                },
                onLibresClick = {},
                onGruposClick = {
                    navController.navigate(Routes.Groups)
                },
                onLogoutClick = {
                    viewModel.clearSession()
                    navController.navigate(Graph.Auth) {
                        popUpTo(Graph.Main) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Routes.Quimica> {
            QuimicaScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<Routes.Groups> {
            GroupsScreen(
                onGrupoClick = {},
                onLibresClick = {
                    navController.popBackStack()
                },
                onCreateClick = {
                    navController.navigate(Routes.CreateGroup)
                },
                onEditClick = { group ->
                    navController.navigate(
                        Routes.EditGroup(group)
                    )
                },
                onLogoutClick = {
                    viewModel.clearSession()
                    navController.navigate(Graph.Auth) {
                        popUpTo(Graph.Main) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Routes.CreateGroup> {
            CreateGroupScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<Routes.EditGroup> {
            val route =
                it.toRoute<Routes.EditGroup>()
            EditGroupScreen(
                groupName = route.groupName,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}