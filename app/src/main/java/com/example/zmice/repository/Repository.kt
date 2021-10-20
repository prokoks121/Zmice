package com.example.zmice.repository

import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Zmica
import com.example.zmice.models.factory.Factory
import com.example.zmice.polje.Polje

object Repository {


    private var setting = DefaultSettings()

    fun setSettings(settings: DefaultSettings = DefaultSettings()){
        setting = settings
    }
    fun getSettings():DefaultSettings{
        return setting
    }

    fun getMapaPolja( settings: DefaultSettings,
                      pozicijaY: ArrayList<Int>):ArrayList<Polje>
    {
        return  Factory.generateMapaPolja(settings,pozicijaY)
    }

    fun getZmica(settings: DefaultSettings): Zmica {
        return Factory.generateZmica(settings)
    }


}