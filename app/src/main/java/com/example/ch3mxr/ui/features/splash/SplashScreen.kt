package com.example.ch3mxr.ui.features.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ch3mxr.ui.components.AnimatedFlask
import com.example.ch3mxr.ui.features.auth.LogoGlow
import com.example.ch3mxr.ui.features.main.ParticleBackground
import com.example.ch3mxr.ui.theme.Octosquares
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val logoScale = remember {
        Animatable(1f)
    }

    val textAlpha = remember {
        Animatable(1f)
    }

    LaunchedEffect(startAnimation) {

        if (startAnimation) {

            logoScale.animateTo(
                targetValue = 1.8f,
                animationSpec = tween(700)
            )

            textAlpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(500)
            )

            delay(300)

            onFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF0B255F)
                    )
                )
            )
    ) {

        ParticleBackground()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .scale(logoScale.value)
                    .clickable {

                        startAnimation = true
                    },
                contentAlignment = Alignment.Center
            ) {

                LogoGlow()

                AnimatedFlask()
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Text(
                text = "ChemXR",
                color = Color.White,
                fontFamily = Octosquares,
                fontSize = 38.sp,
                modifier = Modifier.alpha(textAlpha.value)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Toca el matraz para iniciar",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                modifier = Modifier.alpha(textAlpha.value)
            )
        }
    }
}