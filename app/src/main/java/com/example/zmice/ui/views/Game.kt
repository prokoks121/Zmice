package com.example.zmice.ui.views

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.MotionEvent
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zmice.GameApplication
import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Zmica
import com.example.zmice.polje.Polje
import com.example.zmice.polje.PoljeType
import com.example.zmice.ui.viewmodels.GameViewModel
import com.example.zmice.ui.views.Angle.fromAngle
import com.example.zmice.ui.views.Angle.getAngle
import com.example.zmice.ui.views.Angle.getDistance
import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.math.sqrt

class Game(val settings: DefaultSettings, val mapaPolja: ArrayList<Polje>, val zmica: Zmica){
    var foodPosition = arrayListOf(settings.defFoodPosition[0],settings.defFoodPosition[1])
    var growPosition = arrayListOf<Int>()
    var canMove = true



    fun getFromXY(y:Int,x:Int):Polje{
        val i = y*settings.x + x;
        return mapaPolja[i]
    }

    fun uvecatiZmijcu(onCall:(Int)-> Unit){
        zmica.x.add(0,growPosition[0])
        zmica.y.add(0,growPosition[1])
        getFromXY(growPosition[0],growPosition[1]).color = Color.White
        getFromXY(growPosition[0],growPosition[1]).type = PoljeType.ZMIJCA
        zmica.duzinaZmijce = zmica.duzinaZmijce + 1
        growPosition.removeFirst()
        growPosition.removeFirst()
        onCall(100)
    }

    fun checkCollision(OnCollisn: () -> Unit){
        CoroutineScope(Dispatchers.Main).launch {

            if (settings.wall && (zmica.x[zmica.duzinaZmijce-1] == 0 || zmica.y[zmica.duzinaZmijce-1] == 0 || zmica.x[zmica.duzinaZmijce-1] == settings.y-1 || zmica.y[zmica.duzinaZmijce-1] == settings.x-1)) {
                getFromXY(zmica.x[zmica.duzinaZmijce - 1],zmica.y[zmica.duzinaZmijce - 1]).color = Color.Red
                canMove = false
                    delay(500L)
                    OnCollisn()
            }
            else
                for (i in 0 until zmica.duzinaZmijce-1){
                    if (zmica.x[i] == zmica.x[zmica.duzinaZmijce-1] && zmica.y[i] == zmica.y[zmica.duzinaZmijce-1]){
                        getFromXY(zmica.x[zmica.duzinaZmijce-1],zmica.y[zmica.duzinaZmijce-1]).color = Color.Red
                        canMove = false
                        delay(500L)
                        OnCollisn()

                    }
                }
        }
    }

    fun growCheck():Boolean {

        if (!growPosition.isEmpty()) {
            return zmica.checkIsTail(growPosition[0],growPosition[1])
        }else
            return false
    }

    fun foodCheck(){
        if (foodPosition.isNotEmpty()) {
            if (zmica.checkIsHead(foodPosition[0],foodPosition[1])){
                growPosition.add(foodPosition[0])
                growPosition.add(foodPosition[1])
                foodPosition.removeFirst()
                foodPosition.removeFirst()
                addFood()
            }
        }
    }

    fun pomeriNaDole(){
        pomeranje(1,0)
    }
    fun pomeriNaGore(){
        pomeranje(-1,0)
    }
    fun pomeriUDesno(){
        pomeranje(0,1)
    }
    fun pomeriULevo(){
        pomeranje(0,-1)
    }

    fun pomeranje(x:Int,y: Int){

        getFromXY(zmica.x[0],zmica.y[0]).color = Color.Gray
        var moveX = zmica.x[zmica.duzinaZmijce-1]+x
        var moveY = zmica.y[zmica.duzinaZmijce-1]+y
        if (!settings.wall) {
            if (moveX > settings.y - 1)
                moveX = 0
            else if (moveX < 0)
                moveX = settings.y - 1
            if (moveY > settings.x - 1)
                moveY = 0
            else if (moveY < 0)
                moveY = settings.x - 1
        }
        getFromXY(moveX,moveY).color = Color.White
        getFromXY(zmica.x[0],zmica.y[0]).type = PoljeType.SLOBODNO
        getFromXY(moveX,moveY).type = PoljeType.ZMIJCA
        zmica.x.add(moveX)
        zmica.y.add(moveY)
        zmica.y.removeAt(0)
        zmica.x.removeAt(0)
    }

    fun addFood(){
        var x = 0
        var y = 0
        while (true){
            x = (0 until settings.y).random()
            y = (0 until settings.x).random()
            if (
                (getFromXY(x,y).type != PoljeType.ZMIJCA)
                && (foodPosition.isEmpty() || x != foodPosition[0] && foodPosition[1] != y)
                && getFromXY(x,y).type != PoljeType.ZID
            )
                break
        }
        getFromXY(x,y).type = PoljeType.HRANA
        getFromXY(x,y).color = Color.Green
        foodPosition.add(x)
        foodPosition.add(y)
    }
}

@ExperimentalComposeUiApi
@Composable
fun Zmijce(
    OnCollisn:(Int) -> Unit,
   application:Application
){

    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val repository = (application as GameApplication).repository

            @Suppress("UNCHECKED_CAST")
            return GameViewModel(
                repository = repository
            ) as T
        }
    }

    val viewModel: GameViewModel = viewModel(factory = factory)

    val settings by viewModel.settings.observeAsState()
    val zmica by viewModel.zmica.observeAsState()
    val mapaPolja by viewModel.mapaPolja.observeAsState()
    var scores by remember {
        mutableStateOf(viewModel.score)
    }

    val game by remember {
        mutableStateOf(mapaPolja?.let { settings?.let { it1 -> zmica?.let { it2 -> Game(settings = it1,mapaPolja = it,zmica = it2) } } })
    }
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
    Canvas(modifier = Modifier
        .size(size+offset)){
        drawRoundRect(
            color = type.color,
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
fun Mapa(game:Game, scores: Int, onClick:(Int) -> Unit, updateScore:(Int) -> Unit){

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

object Angle{
    fun getAngle(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        val rad = Math.atan2((y1 - y2).toDouble(), (x2 - x1).toDouble()) + Math.PI
        return (rad * 180 / Math.PI + 180) % 360
    }
    fun inRange(angle: Double, init: Float, end: Float): Boolean {
        return angle >= init && angle < end
    }
    fun fromAngle(angle: Double): Int {
        return if (inRange(angle, 45F, 135F)) {
            1
        } else if (inRange(angle, 0F, 45F) || inRange(angle, 315F, 360F)) {
            2
        } else if (inRange(angle, 225F, 315F)) {
            3
        } else {
            4
        }
    }
    fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float):Float{
        val distance = sqrt((x1-x2).pow(2) + (y1-y2).pow(2))
        return distance
    }

}


