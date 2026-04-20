package com.example.ch3mxr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ch3mxr.ui.theme.Ch3mxrTheme
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ch3mxrTheme {
                // Estados para controlar la navegación
                var currentScreen by remember { mutableStateOf("login") }

                // Eliminamos el Scaffold aquí para que el gradiente del Login
                // ocupe toda la pantalla sin interferencias.
                when (currentScreen) {
                    "login" -> {
                        LoginScreenImproved( // Llamamos a la nueva función
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
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ch3mxrTheme {
        Greeting("Android")
    }
}