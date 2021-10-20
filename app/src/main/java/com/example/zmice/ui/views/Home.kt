package com.example.zmice.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.zmice.R
import com.example.zmice.models.DefaultSettings
import com.example.zmice.repository.Repository

@Composable
fun home(navigateTo:(String)->Unit){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black),
    ) {
        Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
            Column() {
                Row() {

                        Image(modifier = Modifier
                            .padding(10.dp)
                            .width(100.dp)
                            .height(55.dp)
                            .clickable {
                                Repository.setSettings(DefaultSettings(wall = true))
                                navigateTo("Game")
                            }
                            .clip(shape = RoundedCornerShape(10.dp)),
                            painter = painterResource(id = R.drawable.wall),
                            contentDescription = "wall",
                            contentScale = ContentScale.Crop)

                    Image(modifier = Modifier
                        .padding(10.dp)
                        .width(100.dp)
                        .height(55.dp)
                        .clickable {
                            Repository.setSettings(DefaultSettings(wall = false))
                            navigateTo("Game")
                        }
                        .clip(shape = RoundedCornerShape(10.dp)),
                        painter = painterResource(id = R.drawable.island),
                        contentDescription = "wall",
                        contentScale = ContentScale.Crop)
                }

               /* Button(colors = ButtonDefaults.buttonColors(Color.Red),
                    onClick = { navigateTo("Settings") }) {
                    Text(text = "Settings")
                }*/
            }

        }
    }
}
@Preview
@Composable
fun prew(){
    home(navigateTo = {

    })
}
