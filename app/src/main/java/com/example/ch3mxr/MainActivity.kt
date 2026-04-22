package com.example.ch3mxr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.ch3mxr.ui.theme.Ch3mxrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Ch3mxrTheme {

                var currentScreen by remember { mutableStateOf("login") }
                var selectedGroup by remember { mutableStateOf("") }

                when (currentScreen) {

                    "login" -> {
                        LoginScreenImproved(
                            onLoginSuccess = { currentScreen = "home" },
                            onRegisterClick = { currentScreen = "register" }
                        )
                    }

                    "register" -> {
                        RegisterScreen(
                            onRegisterSuccess = { currentScreen = "login" },
                            onBackToLogin = { currentScreen = "login" }
                        )
                    }

                    "home" -> {
                        HomeScreen(
                            onQuimicaClick = { currentScreen = "quimica" },
                            onLibresClick = { currentScreen = "home" },
                            onGruposClick = { currentScreen = "grupos" }
                        )
                    }

                    "quimica" -> {
                        QuimicaScreen(
                            onBack = { currentScreen = "home" }
                        )
                    }

                    "grupos" -> {
                        GroupsScreen(
                            onGrupoClick = {},
                            onLibresClick = { currentScreen = "home" },
                            onEditClick = { grupo ->
                                selectedGroup = grupo
                                currentScreen = "editGroup"
                            },
                            onCreateClick = {
                                currentScreen = "createGroup"
                            }
                        )
                    }

                    "editGroup" -> {
                        EditGroupScreen(
                            groupName = selectedGroup,
                            onBack = { currentScreen = "grupos" }
                        )
                    }

                    "createGroup" -> {
                        CreateGroupScreen(
                            onBack = { currentScreen = "grupos" }
                        )
                    }
                }
            }
        }
    }
}