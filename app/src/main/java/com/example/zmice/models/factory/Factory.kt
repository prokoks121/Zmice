package com.example.zmice.models.factory

import androidx.compose.ui.graphics.Color
import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Zmica
import com.example.zmice.polje.Polje
import com.example.zmice.polje.PoljeType

object Factory {

    fun generateMapaPolja(
        settings: DefaultSettings,
        pozicijaX:ArrayList<Int>

    ):ArrayList<Polje>{
        val mapaPolja = ArrayList<Polje>()
        val ukupno = settings.x*settings.y
        val sredina = settings.x/2
        for (n in 0 until ukupno){
            val i = n/settings.x
            val j = n%settings.x
                if (settings.wall && (i == 0 || j == 0 || i == settings.y-1 || j == settings.x-1))
                    mapaPolja.add(Polje(PoljeType.ZID, Color.Red))
                else if (i in pozicijaX && j == sredina)
                    mapaPolja.add(Polje(PoljeType.ZMIJCA, Color.White))
                else if (i == settings.defFoodPosition[0] && j == settings.defFoodPosition[1])
                    mapaPolja.add(Polje(PoljeType.HRANA, Color.Green))
                else
                    mapaPolja.add(Polje(PoljeType.SLOBODNO, Color.Gray))

        }
        return mapaPolja
    }




    fun generateZmica(settings: DefaultSettings):Zmica{
        val zmica = Zmica(duzinaZmijce = settings.duzina)
        val sredina = settings.x/2
        for (i in 0 until  settings.duzina){
            zmica.x.add(4+i)
            zmica.y.add(sredina)
        }
        return zmica
    }
}