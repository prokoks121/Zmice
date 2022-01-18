package com.example.zmice.logic

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Polje
import com.example.zmice.models.PoljeType
import com.example.zmice.models.Zmica
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameLogic(val settings: DefaultSettings, val mapaPolja: SnapshotStateList<Polje>, val zmica: Zmica){

    var foodPosition = arrayListOf(settings.defFoodPosition[0],settings.defFoodPosition[1])
    var growPosition = arrayListOf<Int>()
    var canMove = true

    fun getFromXY(y:Int,x:Int,polje: Polje){
        val i = y*settings.x + x
        mapaPolja.set(index = i, element = polje)
    }

    fun getFromXY(y:Int,x:Int): Polje {
        val i = y*settings.x + x
        return mapaPolja[i]
    }

    fun uvecatiZmijcu(onCall:(Int)-> Unit){
        zmica.x.add(0,growPosition[0])
        zmica.y.add(0,growPosition[1])
        getFromXY(growPosition[0],growPosition[1],
            Polje(color = Color.White, type = PoljeType.ZMIJCA)
        )
        zmica.duzinaZmijce = zmica.duzinaZmijce + 1
        growPosition.removeFirst()
        growPosition.removeFirst()
        onCall(100)
    }

    fun checkCollision(OnCollisn: () -> Unit){
        CoroutineScope(Dispatchers.Main).launch {

            if (settings.wall && (zmica.x[zmica.duzinaZmijce-1] == 0 || zmica.y[zmica.duzinaZmijce-1] == 0 || zmica.x[zmica.duzinaZmijce-1] == settings.y-1 || zmica.y[zmica.duzinaZmijce-1] == settings.x-1)) {
                val polje = Polje(color = Color.Red, type = PoljeType.ZMIJCA)
                getFromXY(zmica.x[zmica.duzinaZmijce - 1],zmica.y[zmica.duzinaZmijce - 1],
                    Polje(color = Color.Red, type = PoljeType.ZMIJCA)
                )
                canMove = false
                delay(500L)
                OnCollisn()
            }
            else
                for (i in 0 until zmica.duzinaZmijce-1){
                    if (zmica.x[i] == zmica.x[zmica.duzinaZmijce-1] && zmica.y[i] == zmica.y[zmica.duzinaZmijce-1]){
                        val polje = Polje(color = Color.Red, type = PoljeType.ZMIJCA)
                        getFromXY(zmica.x[zmica.duzinaZmijce-1],zmica.y[zmica.duzinaZmijce-1],polje)
                        canMove = false
                        delay(500L)
                        OnCollisn()

                    }
                }
        }
    }

    fun growCheck():Boolean {
        return if (!growPosition.isEmpty())
            zmica.checkIsTail(growPosition[0],growPosition[1])
        else
            false
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
        val polje = Polje(color = Color.Gray, type = PoljeType.SLOBODNO)
        getFromXY(zmica.x[0],zmica.y[0], polje = polje)
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

        getFromXY(moveX,moveY, Polje(PoljeType.ZMIJCA, Color.White))
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

        getFromXY(x,y, Polje(PoljeType.HRANA, Color.Green))
        foodPosition.add(x)
        foodPosition.add(y)
    }
}