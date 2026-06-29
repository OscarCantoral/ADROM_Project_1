package com.example.ch3mxr.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ch3mxr.R
import kotlinx.coroutines.delay

@Composable
fun AnimatedFlask() {

    val infiniteTransition = rememberInfiniteTransition(
        label = ""
    )

    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    var frameIndex by remember {
        mutableStateOf(0)
    }

    val frames = listOf(
        R.drawable.e0,
        R.drawable.e1,
        R.drawable.e2,
        R.drawable.e3,
        R.drawable.e4,
        R.drawable.e5,
        R.drawable.e6,
        R.drawable.e7,
        R.drawable.e8,
        R.drawable.e9,
        R.drawable.e10,
        R.drawable.e11,
        R.drawable.e12,
        R.drawable.e13,
        R.drawable.e14,
        R.drawable.e15,
        R.drawable.e16,
        R.drawable.e17,
        R.drawable.e18
    )

    LaunchedEffect(Unit) {

        while (true) {

            frameIndex++

            if (frameIndex >= frames.size)
                frameIndex = 0

            delay(90)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.offset(
            y = (-70).dp + floatOffset.dp
        )
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.matraz
            ),
            contentDescription = null,
            modifier = Modifier.size(159.dp)
        )

        Image(
            painter = painterResource(
                id = frames[frameIndex]
            ),
            contentDescription = null,
            modifier = Modifier.size(159.dp)
        )
    }
}