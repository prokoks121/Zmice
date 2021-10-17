package com.example.zmice

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zmice.ui.theme.ZmiceTheme
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zmice.polje.Polje
import com.example.zmice.polje.PoljeType
import com.example.zmice.ui.views.Zmijce
import com.example.zmice.ui.views.home
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

/*
val vremePoteza = 3000L
var kolone = 25
var redovi = 50
var size:Dp = 13.dp
var offset:Dp = 2.dp
var canMove = true
var wall = false
*/
class MainActivity : ComponentActivity() {

    var mapaPolja:ArrayList<ArrayList<Polje>> = ArrayList()
    var pozicijaX = arrayListOf(5,6,7,8,9,10)
    var pozicijaY = arrayListOf(10,10,10,10,10,10)
    var foodPosition = arrayListOf(14,10)
    var duzinaZmijce = 6
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val navigation = rememberNavController()
            ZmiceTheme {
                Navigation(navigation = navigation)

            }
        }



    }
/*
    fun uvecatiZmijcu(){
        pozicijaX.add(0,foodPosition[0])
        pozicijaY.add(0,foodPosition[1])
        mapaPolja[foodPosition[0]][foodPosition[1]].color = Color.White
        mapaPolja[foodPosition[0]][foodPosition[1]].type = PoljeType.ZMIJCA
        duzinaZmijce++
        Log.d("Provera",duzinaZmijce.toString())
    }

    fun restart(){
        pozicijaX = arrayListOf(5,6,7,8,9,10)
        pozicijaY = arrayListOf(10,10,10,10,10,10)
        foodPosition = arrayListOf(14,10)
        duzinaZmijce = 6
        restartMap(mapaPolja)
        canMove = true
    }


    fun checkCollision(){

    CoroutineScope(Dispatchers.Default).launch {
        if (pozicijaX[duzinaZmijce-1] == 0 || pozicijaY[duzinaZmijce-1] == 0 || pozicijaX[duzinaZmijce-1] == redovi-1 || pozicijaY[duzinaZmijce-1] == kolone-1) {
            mapaPolja[pozicijaX[duzinaZmijce - 1]][pozicijaY[duzinaZmijce - 1]].color = Color.Red
            canMove = false
            CoroutineScope(Dispatchers.Default).launch {
                delay(5000L)
                restart()
            }
        }
        else
        for (i in 0 until duzinaZmijce-1){
        if (pozicijaX[i] == pozicijaX[duzinaZmijce-1] && pozicijaY[i] == pozicijaY[duzinaZmijce-1]){
            mapaPolja[pozicijaX[duzinaZmijce-1]][pozicijaY[duzinaZmijce-1]].color = Color.Red
            canMove = false
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
                if (i == 0 || j == 0 || i == redovi-1 || j == kolone-1)
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

    fun restartMap(mapaPolja:ArrayList<ArrayList<Polje>>){

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

        mapaPolja[pozicijaX[0]][pozicijaY[0]].color = Color.Gray


        var moveX = pozicijaX[duzinaZmijce-1]+x
        var moveY = pozicijaY[duzinaZmijce-1]+y
if (wall) {
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
            if ((mapaPolja[x][y].type != PoljeType.ZMIJCA) && (x != foodPosition[0] && foodPosition[1] != y))
                break
        }
        mapaPolja[x][y].type = PoljeType.HRANA
        mapaPolja[x][y].color = Color.Green
        foodPosition[0] = x
        foodPosition[1] = y
    }*/
}



@ExperimentalComposeUiApi
@Composable
fun Navigation(navigation:NavHostController){
    NavHost(navController = navigation, startDestination = "Home"){
        composable("Home"){
            home{
                navigation.navigate("Game")
            }
        }
        composable("Game"){
            Zmijce{
                navigation.navigate("Home")
            }
        }
    }

}
/*
@SuppressLint("UnrememberedMutableState")
@Composable
fun polje(offset: Dp,size:Dp,polje:Polje){



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
                )
            )

    }


}


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





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {


    //mapa(kolone = kolone, redovi = redovi, offset = offset, size = size)
    // polje(offset = 1.dp, size = 1.dp, id = 1)
}


@ExperimentalComposeUiApi
@Composable
fun mapa(mapaPolja: ArrayList<ArrayList<Polje>>,onClick:(Int) -> Unit){

    var direction by remember {
        mutableStateOf(3)
    }
    var text by remember {
        mutableStateOf(1)
    }
    var x1 = 0F
    var y1 = 0F
    var x2 = 0F
    var y2 = 0F

    LaunchedEffect(text)  {
        if (canMove) {
            delay(vremePoteza)
            text += 1
            onClick(direction)
        }
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFF131313))
        .pointerInteropFilter {
            if (canMove) {

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
                                text += 1
                                onClick(newDirection)
                            }
                        } else {

                            text += 1
                            onClick(direction)
                        }

                    }
                    else -> false
                }
            }
            true
        }) {
        Column() {
            Text(text = text.toString())
            for (i in 0 until  redovi){
                Row() {
                    for (j in 0 until kolone){
                        polje(offset = offset, size = size, mapaPolja[i][j])
                    }
                }
            }
        }

    }
}

*/


