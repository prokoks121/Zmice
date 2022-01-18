package com.example.zmice.moduls

import com.example.zmice.database.ScoreDao
import com.example.zmice.repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModul {

    @Singleton
    @Provides
    fun provideRepository(
    scoreDao: ScoreDao
    ):GameRepository{
        return GameRepository(scoreDao)
    }
}