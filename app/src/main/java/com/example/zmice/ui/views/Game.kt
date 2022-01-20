package com.example.zmice.ui.views

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zmice.logic.Angle.fromAngle
import com.example.zmice.logic.Angle.getAngle
import com.example.zmice.logic.Angle.getDistance
import com.example.zmice.logic.GameLogic
import com.example.zmice.models.Polje
import com.example.zmice.ui.viewmodels.GameViewModel
import kotlinx.coroutines.*

@ExperimentalComposeUiApi
@Composable
fun Zmice(
    OnCollisn:(Int) -> Unit,
    viewModel: GameViewModel = hiltViewModel()
){
    val settings by viewModel.settings.observeAsState()
    val zmica by viewModel.zmica.observeAsState()
    val mapaPolja = viewModel.mapaPolja
    var scores by remember {
        mutableStateOf(viewModel.score)
    }

    val game = mapaPolja?.let { settings?.let { it1 -> zmica?.let { it2 -> GameLogic(
            settings = it1,
            mapaPolja = it,
            zmica = it2) } } }

    Surface(color = MaterialTheme.colors.background) {
        game?.let {game->
            Mapa(game = game,scores = scores,
            updateScore = {
                scores += it
                viewModel.score = scores
            },onClick = {
                    val growCheck = game.growCheck()
                    when (it) {
                        1 -> {
                            game.pomeriNaGore()
                        }
                        2 -> {
                            game.pomeriUDesno()
                        }
                        3 -> {
                            game.pomeriNaDole()
                        }
                        4 -> {
                            game.pomeriULevo()
                        }
                    }
                    game.checkCollision { OnCollisn(scores) }
                    if (growCheck) {
                        game.uvecatiZmijcu(onCall = {score->
                           scores +=score
                           viewModel.score = scores
                        })
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        game.foodCheck()
                    }
                })
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Polje(offset: Dp, size: Dp, polje: Polje){
    val type by mutableStateOf(polje)
    val animation by animateColorAsState(targetValue = type.color,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ))
    Canvas(modifier = Modifier
        .size(size+offset)){
        drawRoundRect(
            color = animation,
            size = Size(
                width = size.toPx(),
                height = size.toPx()
            ),
            cornerRadius = CornerRadius(
                x = offset.toPx(),
                y =  offset.toPx()
            ))
    }
}

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@ExperimentalComposeUiApi
@Composable
fun Mapa(game:GameLogic, scores: Int, onClick:(Int) -> Unit, updateScore:(Int) -> Unit){

    var direction by remember {
        mutableStateOf(3)
    }
   val score by  mutableStateOf(scores)

    var x1 = 0F
    var y1 = 0F
    var x2 = 0F
    var y2 = 0F


   LaunchedEffect(score)  {
        if (game.canMove) {
            delay(game.settings.vremePoteza)
            updateScore(1)
            onClick(direction)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF131313))
            .pointerInteropFilter {
                if (game.canMove) {

                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            x1 = it.x
                            y1 = it.y
                        }
                        MotionEvent.ACTION_UP -> {
                            x2 = it.x
                            y2 = it.y
                            if (x1 != x2 || y1 != y2) {
                                val newDirection = fromAngle(getAngle(x1, y1, x2, y2))
                                if (getDistance(x1, y1, x2, y2) < game.settings.distance) {
                                    updateScore(1)
                                    onClick(direction)
                                } else
                                    if (!(newDirection != direction && direction % 2 == newDirection % 2)) {
                                        direction = newDirection
                                        updateScore(1)
                                        onClick(newDirection)
                                    }
                            } else {
                                updateScore(1)
                                onClick(direction)
                            }
                        }
                        else -> false
                    }
                }
                true
            }) {
        Column(modifier = Modifier.fillMaxSize()) {
           Score(score = score)
            Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()) {
                Column() {
                    for (i in 0 until game.settings.y) {
                        Row() {
                            for (j in 0 until game.settings.x) {
                                Polje(
                                    offset = game.settings.offset,
                                    size = game.settings.size,
                                    game.getFromXY(i, j)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun Score(score:Int){
    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp),
        text = "Score",
        fontSize = 35.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
            text = score.toString(),
            fontSize = 35.sp,
            color = Color.White,
        textAlign = TextAlign.Center)

}




