package com.example.zmice.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun home(onClick:()->Unit){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black),
    ) {
        Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
            Button(colors = ButtonDefaults.buttonColors(Color.Green),
                onClick = { onClick() }) {
                Text(text = "Start")
            }
        }
    }
}
