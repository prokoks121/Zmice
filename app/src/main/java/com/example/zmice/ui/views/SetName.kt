package com.example.zmice.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SetName(setName:(String) ->Unit){
    var text by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()
    ,contentAlignment = Alignment.Center
        ) {
        Column() {
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
                text = "Vaše ime",
                fontSize = 35.sp,
                color = Color.White,
                textAlign = TextAlign.Center)
            TextField(value = text, onValueChange = {text = it})
        if(text != "")
            Box(modifier = Modifier
                .padding(15.dp)
                .background(color = Color.White,shape = RoundedCornerShape(15.dp))
                .clickable {
                    setName(text)
                }
                ) {
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                    text = "Sacuvaj",
                    fontSize = 25.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center)
            }
        }
    }

}