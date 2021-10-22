package com.example.zmice.ui.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zmice.GameApplication
import com.example.zmice.R
import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Score
import com.example.zmice.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Home(navigateTo:(String)->Unit, application: GameApplication,score:State<Score?>){

    val bestScore by remember(score.value){
        mutableStateOf(score.value)
    }


    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black),
    ) {
        Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BestScore(bestScore)
                Row() {

                        Image(modifier = Modifier
                            .padding(10.dp)
                            .width(100.dp)
                            .height(55.dp)
                            .clickable {
                                application.repository.setSettings(DefaultSettings(wall = true))
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
                            application.repository.setSettings(DefaultSettings(wall = false))
                            navigateTo("Game")
                        }
                        .clip(shape = RoundedCornerShape(10.dp)),
                        painter = painterResource(id = R.drawable.island),
                        contentDescription = "wall",
                        contentScale = ContentScale.Crop)
                }
            }
        }
    }
}

@Composable
fun BestScore(score: Score? = Score(0,50,"Miki")){
    Text(modifier = Modifier
        .padding(bottom = 10.dp),
        text = "BestScore",
        fontSize = 35.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    Row(modifier = Modifier
        .padding( bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically) {
        score?.let {
            Text(modifier = Modifier,
                text = it.name,
                fontSize = 35.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold)

        Text(modifier = Modifier
            .padding(start = 10.dp),
            text = it.score.toString(),
            fontSize = 35.sp,
            color = Color.White,
            textAlign = TextAlign.Center)
            }
    }
}


