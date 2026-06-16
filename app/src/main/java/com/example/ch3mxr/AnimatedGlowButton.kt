package com.example.ch3mxr

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ch3mxr.ui.theme.Octosquares

@Composable
fun AnimatedGlowButton(
    text: String,
    onClick: () -> Unit
) {

    val transition = rememberInfiniteTransition(
        label = ""
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                easing = LinearEasing
            )
        ),
        label = ""
    )

    val gradientOffset by transition.animateFloat(
        initialValue = -300f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                easing = LinearEasing
            )
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            val strokeWidth = 5.dp.toPx()

            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF00D4FF),
                        Color(0xFF00D4FF),
                        Color(0xFF00D4FF),
                        Color(0xFF00D4FF),
                        Color(0xFF00D4FF),
                        Color(0xFF8B5CF6),
                        Color(0xFF00D4FF),
                        Color(0xFF00D4FF),
                        Color(0xFF00D4FF),
                        Color(0xFF00D4FF),
                        Color(0xFF00D4FF)
                    ),
                    start = Offset(
                        gradientOffset,
                        0f
                    ),
                    end = Offset(
                        gradientOffset + size.width,
                        size.height
                    )
                ),
                style = Stroke(strokeWidth),
                cornerRadius = CornerRadius(12.dp.toPx())
            )

            val perimeter =
                (size.width * 2f) +
                        (size.height * 2f)

            val position =
                perimeter * progress

            val glowPosition = when {

                position <= size.width -> {

                    Offset(
                        position,
                        0f
                    )
                }

                position <= size.width + size.height -> {

                    Offset(
                        size.width,
                        position - size.width
                    )
                }

                position <= (size.width * 2f) + size.height -> {

                    Offset(
                        size.width -
                                (position - size.width - size.height),
                        size.height
                    )
                }

                else -> {

                    Offset(
                        0f,
                        size.height -
                                (position - (size.width * 2f) - size.height)
                    )
                }
            }

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFF8B5CF6),
                        Color.Transparent
                    )
                ),
                radius = 40.dp.toPx(),
                center = glowPosition
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .background(
                    color = Color(0xFF081A3D),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                val pulse by transition.animateFloat(
                    initialValue = 0.3f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            1000
                        ),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = ""
                )

                Canvas(
                    modifier = Modifier.size(10.dp)
                ) {

                    drawCircle(
                        color = Color(0xFF00D4FF).copy(alpha = pulse)
                    )
                }

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Text(
                    text = text,
                    fontFamily = Octosquares,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}