package com.example.ch3mxr

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.math.sqrt
import kotlin.random.Random

data class Particle(
    val x: Float,
    val y: Float,
    val dx: Float,
    val dy: Float,
    val radius: Float
)

data class BigParticle(
    val x: Float,
    val y: Float,
    val dx: Float,
    val dy: Float,
    val radius: Float
)

@Composable
fun ParticleBackground() {

    var particles by remember {

        mutableStateOf(
            List(45) {

                Particle(
                    x = Random.nextFloat() * 1200f,
                    y = Random.nextFloat() * 2400f,
                    dx = Random.nextFloat() * 2.5f - 1.5f,
                    dy = Random.nextFloat() * 2.5f - 1.5f,
                    radius = Random.nextFloat() * 8f + 4f
                )
            }
        )
    }

    var bigParticles by remember {

        mutableStateOf(
            List(20) {

                BigParticle(
                    x = Random.nextFloat() * 1200f,
                    y = Random.nextFloat() * 2400f,
                    dx = Random.nextFloat() * 1.0f - 0.6f,
                    dy = Random.nextFloat() * 1.0f - 0.6f,
                    radius = Random.nextFloat() * 20f + 15f
                )
            }
        )
    }

    LaunchedEffect(Unit) {

        while (true) {

            particles = particles.map { p ->

                var newX = p.x + p.dx
                var newY = p.y + p.dy

                var newDx = p.dx
                var newDy = p.dy

                if (newX < 0f || newX > 1200f)
                    newDx *= -1

                if (newY < 0f || newY > 2400f)
                    newDy *= -1

                Particle(
                    x = newX,
                    y = newY,
                    dx = newDx,
                    dy = newDy,
                    radius = p.radius
                )
            }

            bigParticles = bigParticles.map { p ->

                var newX = p.x + p.dx
                var newY = p.y + p.dy

                var newDx = p.dx
                var newDy = p.dy

                if (newX < 0f || newX > 1200f)
                    newDx *= -1

                if (newY < 0f || newY > 2400f)
                    newDy *= -1

                BigParticle(
                    x = newX,
                    y = newY,
                    dx = newDx,
                    dy = newDy,
                    radius = p.radius
                )
            }

            delay(16)
        }
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        bigParticles.forEach { particle ->

            drawCircle(
                color = Color(0xFF29D3FF).copy(alpha = 0.08f),
                radius = particle.radius,
                center = Offset(
                    particle.x,
                    particle.y
                )
            )
        }

        particles.forEach { particle ->

            drawCircle(
                color = Color(0xFF0ED2F7),
                radius = particle.radius,
                center = Offset(
                    particle.x,
                    particle.y
                )
            )
        }

        for (i in particles.indices) {

            for (j in i + 1 until particles.size) {

                val p1 = particles[i]
                val p2 = particles[j]

                val distance = sqrt(
                    ((p1.x - p2.x) * (p1.x - p2.x)) +
                            ((p1.y - p2.y) * (p1.y - p2.y))
                )

                if (distance < 180f) {

                    drawLine(
                        color = Color(0xFF8B5CF6).copy(alpha = 0.15f),
                        start = Offset(p1.x, p1.y),
                        end = Offset(p2.x, p2.y),
                        strokeWidth = 2f
                    )
                }
            }
        }
    }
}