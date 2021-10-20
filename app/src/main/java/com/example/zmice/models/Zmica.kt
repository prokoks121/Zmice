package com.example.zmice.models

data class Zmica(
    val x:ArrayList<Int> = arrayListOf(),
    val y:ArrayList<Int> = arrayListOf(),
    var duzinaZmijce:Int
){
    fun checkIsHead(x:Int,y:Int):Boolean{
        return this.x[duzinaZmijce-1] == x && this.y[duzinaZmijce-1] == y
    }

    fun checkIsTail(x:Int,y:Int):Boolean{
        return this.x[0] == x && this.y[0] == y
    }
}