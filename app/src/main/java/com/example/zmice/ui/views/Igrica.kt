package com.example.zmice.ui.views

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zmice.*
import com.example.zmice.Settings.DefaultSettings
import com.example.zmice.Settings.DefaultSettings.kolone
import com.example.zmice.Settings.DefaultSettings.offset
import com.example.zmice.Settings.DefaultSettings.redovi
import com.example.zmice.Settings.DefaultSettings.size
import com.example.zmice.Settings.DefaultSettings.wall
import com.example.zmice.polje.Polje
import com.example.zmice.polje.PoljeType
import com.example.zmice.ui.views.Angle.fromAngle
import com.example.zmice.ui.views.Angle.getAngle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Game(){

    var mapaPolja:ArrayList<ArrayList<Polje>> = ArrayList()
    var pozicijaX = arrayListOf(5,6,7,8,9,10)
    var pozicijaY = arrayListOf(10,10,10,10,10,10)
    var foodPosition = arrayListOf(14,10)
    var duzinaZmijce = 6
    var canMove = true
    var score = 1



    fun generateMap(){
        mapGenerator(mapaPolja)
    }

    fun uvecatiZmijcu(){
        pozicijaX.add(0,foodPosition[0])
        pozicijaY.add(0,foodPosition[1])
        mapaPolja[foodPosition[0]][foodPosition[1]].color = Color.White
        mapaPolja[foodPosition[0]][foodPosition[1]].type = PoljeType.ZMIJCA
        duzinaZmijce++
        score += 100
        Log.d("Provera",duzinaZmijce.toString())
    }



    fun checkCollision(OnCollisn: () -> Unit){

        CoroutineScope(Dispatchers.Main).launch {
            if (pozicijaX[duzinaZmijce-1] == 0 || pozicijaY[duzinaZmijce-1] == 0 || pozicijaX[duzinaZmijce-1] == redovi-1 || pozicijaY[duzinaZmijce-1] == kolone-1) {
                mapaPolja[pozicijaX[duzinaZmijce - 1]][pozicijaY[duzinaZmijce - 1]].color = Color.Red
                canMove = false
                    delay(500L)
                    OnCollisn()

            }
            else
                for (i in 0 until duzinaZmijce-1){
                    if (pozicijaX[i] == pozicijaX[duzinaZmijce-1] && pozicijaY[i] == pozicijaY[duzinaZmijce-1]){
                        mapaPolja[pozicijaX[duzinaZmijce-1]][pozicijaY[duzinaZmijce-1]].color = Color.Red
                        canMove = false

                        delay(500L)
                        OnCollisn()

                    }
                }
        }
    }
    fun foodCheck():Boolean {
        Log.d("Provera",(pozicijaX[0] == foodPosition[0] && pozicijaY[0] == foodPosition[1]).toString()
                +" " + pozicijaX[0] +" " +pozicijaY[0]  +" " + foodPosition[0] +" " + foodPosition[1]
        )

        return pozicijaX[0] == foodPosition[0] && pozicijaY[0] == foodPosition[1]
    }


    fun mapGenerator(mapaPolja:ArrayList<ArrayList<Polje>>){

        for (i in 0 until redovi){
            mapaPolja.add(ArrayList())
            for (j in 0 until kolone){
                if (wall && (i == 0 || j == 0 || i == redovi-1 || j == kolone-1))
                    mapaPolja.get(i).add(Polje(PoljeType.ZID,Color.Red))
                else if (i in pozicijaX && j == 10)
                    mapaPolja.get(i).add(Polje(PoljeType.ZMIJCA,Color.White))
                else if (i == foodPosition[0] && j == foodPosition[1])
                    mapaPolja.get(i).add(Polje(PoljeType.HRANA,Color.Green))
                else
                    mapaPolja.get(i).add(Polje(PoljeType.SLOBODNO,Color.Gray))
            }
        }
    }

  /*  fun restartMap(mapaPolja:ArrayList<ArrayList<Polje>>){

        for (i in 0 until redovi){
            for (j in 0 until kolone){
                if (i == 0 || j == 0 || i == redovi-1 || j == kolone-1) {
                    mapaPolja.get(i).get(j).type = PoljeType.ZID
                    mapaPolja.get(i).get(j).color = Color.Red
                }
                else if (i in pozicijaX && j == 10) {
                    mapaPolja.get(i).get(j).type = PoljeType.ZMIJCA
                    mapaPolja.get(i).get(j).color = Color.White
                }
                else if (i == foodPosition[0] && j == foodPosition[1]){
                    mapaPolja.get(i).get(j).type = PoljeType.HRANA
                    mapaPolja.get(i).get(j).color = Color.Green
                }
                else{
                    mapaPolja.get(i).get(j).type = PoljeType.SLOBODNO
                    mapaPolja.get(i).get(j).color = Color.Gray
                }
            }
        }
    } */
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

        mapaPolja[pozicijaX[0]][pozicijaY[0]].color = Color.Gray


        var moveX = pozicijaX[duzinaZmijce-1]+x
        var moveY = pozicijaY[duzinaZmijce-1]+y
        if (!wall) {
            if (moveX > redovi - 1)
                moveX = 0
            else if (moveX < 0)
                moveX = redovi - 1
            if (moveY > kolone - 1)
                moveY = 0
            else if (moveY < 0)
                moveY = kolone - 1
        }
        mapaPolja[moveX][moveY].color = Color.White
        mapaPolja[pozicijaX[0]][pozicijaY[0]].type = PoljeType.SLOBODNO
        mapaPolja[moveX][moveY].type = PoljeType.ZMIJCA

        pozicijaX.add(moveX)
        pozicijaY.add(moveY)
        pozicijaY.removeAt(0)
        pozicijaX.removeAt(0)
    }



    fun addFood(){
        var x = 0
        var y = 0
        while (true){
            x = (0 until redovi).random()
            y = (0 until kolone).random()
            if (
                (mapaPolja[x][y].type != PoljeType.ZMIJCA)
                && (x != foodPosition[0] && foodPosition[1] != y)
                && (wall && mapaPolja[x][y].type != PoljeType.ZID)
            )
                break

        }
        mapaPolja[x][y].type = PoljeType.HRANA
        mapaPolja[x][y].color = Color.Green
        foodPosition[0] = x
        foodPosition[1] = y
    }
}



@ExperimentalComposeUiApi
@Composable
fun Zmijce(OnCollisn:() -> Unit){
    val game = Game()
    game.generateMap()

    Surface(color = MaterialTheme.colors.background) {
        mapa(game = game) {
            val foodCheck = game.foodCheck()
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
            if (foodCheck) {
                game.uvecatiZmijcu()
                CoroutineScope(Dispatchers.Default).launch {
                    game.addFood()
                }
            }
            game.checkCollision(OnCollisn)
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun polje(offset: Dp, size: Dp, polje: Polje){
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





@SuppressLint("UnrememberedMutableState")
@ExperimentalComposeUiApi
@Composable
fun mapa(game:Game, onClick:(Int) -> Unit){

    var direction by remember {
        mutableStateOf(3)
    }
    var score = mutableStateOf(game.score)


    var x1 = 0F
    var y1 = 0F
    var x2 = 0F
    var y2 = 0F

    LaunchedEffect(score)  {
        if (game.canMove) {
            delay(DefaultSettings.vremePoteza)
            score.value += 1
            onClick(direction)
        }
    }


    Box(modifier = Modifier
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
                            if (!(newDirection != direction && direction % 2 == newDirection % 2)) {
                                direction = newDirection
                                score.value += 1
                                onClick(newDirection)
                            }
                        } else {

                            score.value += 1
                            onClick(direction)
                        }

                    }
                    else -> false
                }
            }
            true
        }) {
        Column() {
           showScore(score = score.value)
            for (i in 0 until  redovi){
                Row() {
                    for (j in 0 until kolone){
                        polje(offset = offset, size = size, game.mapaPolja[i][j])
                    }
                }
            }
        }

    }
}

@Composable
fun showScore(score:Int){

    Box(modifier = Modifier.fillMaxWidth()
    ,contentAlignment = Alignment.Center){
        Text(text = score.toString(),
            fontSize = 20.sp,
            color = Color.White)
    }
}
@Preview
@Composable
fun score(){
    showScore(score = 52)
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

}


