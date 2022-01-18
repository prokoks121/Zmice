package com.example.zmice.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.zmice.models.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Score::class), version = 5, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract fun scoreDao():ScoreDao

}