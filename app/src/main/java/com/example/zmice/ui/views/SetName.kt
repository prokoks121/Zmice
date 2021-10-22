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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SetName(setName:(String) ->Unit){
    var text by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()
        .background(color = Color(0xFF2E2E2E))
    ,contentAlignment = Alignment.Center
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
                text = "Vaše ime",
                fontSize = 35.sp,
                color = Color.White,
                textAlign = TextAlign.Center)
            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                value = text,
                maxLines = 1,

                onValueChange = {
                    if (it.length <= 8) text = it
                })
        if(text != "")
            Box(modifier = Modifier
                .background(color = Color(0xFFE98B00),shape = RoundedCornerShape(15.dp))
                .clickable {
                    setName(text)
                }
                ) {
                Text(modifier = Modifier
                    .padding(horizontal = 35.dp,vertical = 15.dp),
                    text = "Sacuvaj",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
            }
        }
    }

}
