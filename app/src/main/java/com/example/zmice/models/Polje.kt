package com.example.zmice.models

import androidx.compose.ui.graphics.Color
enum class PoljeType{
    SLOBODNO,ZMIJCA,HRANA,ZID
}

data class Polje(var type: PoljeType, var color:Color)