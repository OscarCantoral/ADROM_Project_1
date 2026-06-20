package com.example.ch3mxr

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.ch3mxr.ui.theme.Ch3mxrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val googleAuthManager = GoogleAuthManager(this)

        setContent {
            Ch3mxrTheme {

                var currentScreen by remember { mutableStateOf("loading") }
                var selectedGroup by remember { mutableStateOf("") }

                when (currentScreen) {

                    "loading" -> {
                        LaunchedEffect(Unit) {
                            val credential = googleAuthManager.tryAutoSignIn()
                            currentScreen = if (credential != null) "home" else "login"
                        }
                    }

                    "login" -> {
                        LoginScreenImproved(
                            onLoginSuccess = { currentScreen = "home" },
                            onRegisterClick = { currentScreen = "register" },
                            googleAuthManager = googleAuthManager
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