package com.example.zmice.moduls

import android.content.Context
import androidx.room.Room
import com.example.zmice.database.GameDatabase
import com.example.zmice.database.ScoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModul {


    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ):GameDatabase{
return Room.databaseBuilder(
    context.applicationContext,
    GameDatabase::class.java,
    "score_database"
).fallbackToDestructiveMigration()
    .build()
    }

    @Singleton
    @Provides
    fun provideDao(
        database: GameDatabase
    ):ScoreDao{
        return database.scoreDao()
    }
}