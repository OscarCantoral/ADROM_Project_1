package com.example.ch3mxr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuimicaScreen(
    onBack: () -> Unit
) {

    val darkBg = Color(0xFF020617)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "QUÍMICA",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 BOTONES (YA SE USA CursoButton)
            CursoButton("ATOMOS") { }
            CursoButton("ENLACES IONICOS") { }
            CursoButton("MINERALES") { }
            CursoButton("OTROS") { }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onBack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0ED2F7)
                )
            ) {
                Text("VOLVER", color = Color.Black)
            }
        }
    }
}