package com.example.ch3mxr

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EditGroupScreen(
    groupName: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Button(
            onClick = { onBack() },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text("Volver")
        }

        Text(
            text = "Editando: $groupName",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}