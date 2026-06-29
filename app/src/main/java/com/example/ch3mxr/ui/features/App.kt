package com.example.ch3mxr.ui.features

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ch3mxr.ui.routes.Graph
import com.example.ch3mxr.ui.routes.appGraph
import com.example.ch3mxr.ui.routes.authGraph
import com.example.ch3mxr.ui.routes.mainGraph

@Composable
fun App(){
    val navController = rememberNavController()

    NavHost(navController, startDestination = Graph.App) {
        appGraph(navController)
        authGraph(navController)
        mainGraph(navController)
    }
}