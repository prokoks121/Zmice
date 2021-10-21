package com.example.zmice.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zmice.models.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY score DESC LIMIT 1")
    fun getScores(): Score

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun addScore(score: Score)
}