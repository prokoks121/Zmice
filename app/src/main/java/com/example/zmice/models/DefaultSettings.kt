package com.example.zmice.models

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DefaultSettings(

    var vremePoteza:Long = 500L,
    var x:Int = 17,
    var y:Int = 22,
    var size: Dp = 13.dp,
    var offset: Dp = 2.dp,
    var disable:Boolean = true,
    var wall:Boolean = false,
    var duzina:Int = 6,
    var defFoodPosition: ArrayList<Int> = arrayListOf(x/2,(y/2)+1),
    var distance:Float = 13f
)

