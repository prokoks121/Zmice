package com.example.zmice.repository

import android.app.AppComponentFactory
import androidx.annotation.WorkerThread
import com.example.zmice.database.ScoreDao
import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Score
import com.example.zmice.models.Zmica
import com.example.zmice.models.factory.Factory
import com.example.zmice.models.Polje
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class GameRepository @Inject constructor(private val scoreDao: ScoreDao) {


    private var setting = DefaultSettings()

    fun setSettings(settings: DefaultSettings = DefaultSettings()){
        setting = settings
    }
    fun getSettings():DefaultSettings{
        return setting
    }
    fun getMapaPolja( settings: DefaultSettings,
                      pozicijaY: ArrayList<Int>):ArrayList<Polje> {
        return  Factory.generateMapaPolja(settings,pozicijaY)
    }
    fun getZmica(settings: DefaultSettings): Zmica {
        return Factory.generateZmica(settings)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getScore():  Score{
        return scoreDao.getScores()
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(score:Score) {
        scoreDao.addScore(score)
    }

}