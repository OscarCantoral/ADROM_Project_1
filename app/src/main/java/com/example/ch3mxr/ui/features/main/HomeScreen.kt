package com.example.ch3mxr.ui.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.ch3mxr.ui.components.CourseCard
import com.example.ch3mxr.R

@Composable
fun HomeScreen(
    onQuimicaClick: () -> Unit,
    onLibresClick: () -> Unit,
    onGruposClick: () -> Unit,
    onLogoutClick: () -> Unit = {}
) {

    val cyanColor = Color(0xFF0ED2F7)
    val darkBg = Color(0xFF020617)

    var selectedTab by remember { mutableStateOf("libres") }

    val cursos = listOf(

        Triple(
            "Química",
            "Átomos, moléculas y enlaces",
            R.drawable.fond_curs_quim
        ),

        Triple(
            "Biología",
            "Células, tejidos y genética",
            R.drawable.fond_curs_biol
        ),

        Triple(
            "Matemáticas",
            "Álgebra, geometría y cálculo",
            R.drawable.fond_curs_mate
        ),

        Triple(
            "Historia",
            "Historia Universal",
            R.drawable.fond_curs_hist
        )

    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CURSOS",
                    color = Color.White
                )

                IconButton(onClick = onLogoutClick) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Cerrar sesión",
                        tint = Color.Red.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            LazyColumn(

                modifier = Modifier.weight(1f),

                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {

                items(cursos) { curso ->

                    CourseCard(

                        titulo = curso.first,

                        descripcion = curso.second,

                        imagen = curso.third,

                        onClick = {

                            if (curso.first == "Química")
                                onQuimicaClick()

                        }

                    )

                }

            }


            // 🔻 FOOTER (alineado correctamente)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // LIBRES
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "PUBLICO",
                        color = if (selectedTab == "libres") cyanColor else Color.Gray,
                        modifier = Modifier.clickable {
                            selectedTab = "libres"
                            onLibresClick()
                        }
                    )

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                            .background(
                                if (selectedTab == "libres") cyanColor else Color.Transparent
                            )
                    )
                }

                // GRUPOS
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "PRIVADOS",
                        color = if (selectedTab == "grupos") cyanColor else Color.Gray,
                        modifier = Modifier.clickable {
                            selectedTab = "grupos"
                            onGruposClick()
                        }
                    )

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                            .background(
                                if (selectedTab == "grupos") cyanColor else Color.Transparent
                            )
                    )
                }
            }
        }
    }
}