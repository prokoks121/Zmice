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
public abstract class GameDatabase : RoomDatabase() {

    abstract fun scoreDao():ScoreDao


    companion object {

        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {

            return INSTANCE
                ?: synchronized(this) {     // if the INSTANCE is not null, then return it,
                    // if it is, then create the database
                    return INSTANCE ?: synchronized(this) {
                        val instance = Room.databaseBuilder(
                            context.applicationContext,
                            GameDatabase::class.java,
                            "score_database"
                        ).fallbackToDestructiveMigration()
                            .build()
                        INSTANCE = instance
                        instance
                    }
                }
        }
    }

}