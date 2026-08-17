package com.example.ch3mxr.ui.features.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ch3mxr.ui.components.PdfComponent

@Composable
fun QuimicaScreen(
    onBack: () -> Unit
) {

    val darkBg = Color(0xFF020617)
    val cyan = Color(0xFF0ED2F7)
    val black = Color(0xFF050505)
    val white = Color(0xFFF8FAFC)

    // Color oficial que quieres usar para PDF
    val pdfRed = Color(0xFFDC3129)
    var isOpened: Boolean by remember {mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
    ) {

        PdfComponent(isOpened)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 16.dp
                )
        ) {

            // =========================
            // CABECERA
            // =========================

            Text(
                text = "QUÍMICA",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Explora los módulos del curso",
                color = Color.White.copy(alpha = 0.55f),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // =========================
            // FILA 1
            // =========================

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ModuleTile(
                    number = "01",
                    title = "ÁTOMOS",
                    subtitle = "Estructura\nde la materia",
                    backgroundColor = black,
                    contentColor = Color.White,
                    borderColor = Color.White.copy(alpha = 0.25f),
                    modifier = Modifier
                        .weight(1.45f)
                        .fillMaxHeight(),
                    onClick = {
                        // Navegar a Átomos
                    }
                )

                ModuleTile(
                    number = "02",
                    title = "ENLACES",
                    subtitle = "Iónicos",
                    backgroundColor = cyan,
                    contentColor = Color.Black,
                    borderColor = cyan,
                    modifier = Modifier
                        .weight(0.85f)
                        .fillMaxHeight(),
                    onClick = {
                        // Navegar a Enlaces
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // =========================
            // FILA 2
            // =========================

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ModuleTile(
                    number = "03",
                    title = "MINERALES",
                    subtitle = "Composición\ny propiedades",
                    backgroundColor = white,
                    contentColor = Color.Black,
                    borderColor = Color.White,
                    modifier = Modifier
                        .weight(0.90f)
                        .fillMaxHeight(),
                    onClick = {
                        // Navegar a Minerales
                    }
                )

                ModuleTile(
                    number = "04",
                    title = "OTROS",
                    subtitle = "Más contenido",
                    backgroundColor = black,
                    contentColor = Color.White,
                    borderColor = cyan,
                    modifier = Modifier
                        .weight(1.40f)
                        .fillMaxHeight(),
                    onClick = {
                        // Navegar a otros
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // =========================
            // MANUAL PDF
            // =========================

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp)
                    .clickable {
                        // Aquí después abriremos el PDF
                        isOpened = true
                    },
                shape = RoundedCornerShape(20.dp),
                color = pdfRed,
                shadowElevation = 4.dp
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Identificador PDF
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White
                    ) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "PDF",
                                color = pdfRed,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "MANUAL",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Material de estudio del curso",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        text = "→",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // =========================
            // INFORMACIÓN
            // =========================

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFF0B1220),
                border = BorderStroke(
                    1.dp,
                    Color.White.copy(alpha = 0.10f)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {

                        Text(
                            text = "PROGRESO DEL CURSO",
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "4 módulos disponibles",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = "●",
                        color = cyan,
                        fontSize = 28.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // =========================
            // BOTÓN VOLVER
            // =========================

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clickable {
                        onBack()
                    },
                shape = RoundedCornerShape(18.dp),
                color = cyan
            ) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "←  VOLVER",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


// =====================================================
// TARJETA DE MÓDULO REUTILIZABLE
// =====================================================

@Composable
private fun ModuleTile(
    number: String,
    title: String,
    subtitle: String,
    backgroundColor: Color,
    contentColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Surface(
        modifier = modifier
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(22.dp),
        color = backgroundColor,
        border = BorderStroke(
            1.dp,
            borderColor
        ),
        shadowElevation = 4.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = number,
                color = contentColor.copy(alpha = 0.50f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Column {

                Text(
                    text = title,
                    color = contentColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = subtitle,
                    color = contentColor.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            Text(
                text = "→",
                color = contentColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}